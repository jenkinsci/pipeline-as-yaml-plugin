package io.jenkins.plugins.pipeline;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import jenkins.branch.BranchSource;
import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;
import jenkins.plugins.git.traits.BranchDiscoveryTrait;
import jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait;
import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries;
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration;
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class PipelineAsYamlWorkflowBranchProjectFactoryTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Rule
    public GitSampleRepoRule sourceCodeRepo = new GitSampleRepoRule();

    @Rule
    public GitSampleRepoRule libraryCodeRepo = new GitSampleRepoRule();

    private String yamlJenkinsFileName = "yamlJenkinsfile.yaml";
    private String[] scmBranches = {"feature", "hotfix"};

    @Test
    public void testWithBranches() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithBranches.yml"), StandardCharsets.UTF_8);
        this.sourceCodeRepo.init();
        this.sourceCodeRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.sourceCodeRepo.git("add", this.yamlJenkinsFileName);
        this.sourceCodeRepo.git("commit", "--all", "--message=InitRepoWithFile");
        for (String branch : this.scmBranches) {
            this.sourceCodeRepo.git("checkout", "-b", branch, "master");
        }
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.sourceCodeRepo.toString());
        sourceCodeRepoSCMSource.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory =
                new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        Assert.assertEquals(this.scmBranches.length + 1, items.size());
        for (WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            String testString = "test-output-" + job.getName();
            System.out.println(run.getLog());
        }
    }

    @Test
    public void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineAllInOne.yml"), StandardCharsets.UTF_8);
        this.sourceCodeRepo.init();
        this.sourceCodeRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.sourceCodeRepo.git("add", this.yamlJenkinsFileName);
        this.sourceCodeRepo.git("commit", "--all", "--message=InitRepoWithFile");
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.sourceCodeRepo.toString());
        sourceCodeRepoSCMSource.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory =
                new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        for (WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            Assert.assertEquals("SUCCESS", run.getResult().toString());
            System.out.println(run.getLog());
        }
    }

    @Test
    public void testWithSharedLibrary() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithLibrary.yml"), StandardCharsets.UTF_8);
        this.sourceCodeRepo.init();
        this.sourceCodeRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.sourceCodeRepo.git("add", this.yamlJenkinsFileName);
        this.sourceCodeRepo.git("commit", "--all", "--message=InitRepoWithFile");

        this.libraryCodeRepo.init();
        this.libraryCodeRepo.mkdirs("vars");
        this.libraryCodeRepo.write("vars/customSteps.groovy", "def customStep(message)  {  echo \"${message}\" }");
        this.libraryCodeRepo.git("add", "vars/customSteps.groovy");
        this.libraryCodeRepo.git("commit", "--all", "--message=InitRepoWithStep");

        GitSCMSource source = new GitSCMSource(this.libraryCodeRepo.toString());
        source.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        LibraryConfiguration sharedLibraryConfiguration =
                new LibraryConfiguration("customSharedLibrary", new SCMSourceRetriever(source));

        GlobalLibraries globalLibraries = GlobalLibraries.get();
        globalLibraries.setLibraries(List.of(sharedLibraryConfiguration));

        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.libraryCodeRepo.toString());
        sourceCodeRepoSCMSource.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory =
                new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        for (WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            this.jenkins.assertLogContains("customEcho", run);
            Assert.assertEquals("SUCCESS", run.getResult().toString());
        }
    }
}
