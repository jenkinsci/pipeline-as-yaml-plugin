package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hudson.model.Result;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import jenkins.branch.BranchSource;
import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;
import jenkins.plugins.git.junit.jupiter.WithGitSampleRepo;
import jenkins.plugins.git.traits.BranchDiscoveryTrait;
import jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait;
import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries;
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration;
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
@WithGitSampleRepo
class PipelineAsYamlWorkflowBranchProjectFactoryTest {

    private JenkinsRule jenkins;
    private GitSampleRepoRule gitRepo;

    private final String yamlJenkinsFileName = "yamlJenkinsfile.yaml";
    private final String[] scmBranches = {"feature", "hotfix"};

    @BeforeEach
    void setUp(JenkinsRule jenkinsRule, GitSampleRepoRule sourceCodeRepo) {
        this.jenkins = jenkinsRule;
        this.gitRepo = sourceCodeRepo;
    }

    @Test
    void testWithBranches() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithBranches.yml"), StandardCharsets.UTF_8);
        this.gitRepo.init();
        this.gitRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.gitRepo.git("add", this.yamlJenkinsFileName);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");
        for (String branch : this.scmBranches) {
            this.gitRepo.git("checkout", "-b", branch, "master");
        }
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.gitRepo.toString());
        sourceCodeRepoSCMSource.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory =
                new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        assertEquals(this.scmBranches.length + 1, items.size());
        for (WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            assertEquals(Result.SUCCESS, run.getResult());
            System.out.println(run.getLog());
        }
    }

    @Test
    void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineAllInOne.yml"), StandardCharsets.UTF_8);
        this.gitRepo.init();
        this.gitRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.gitRepo.git("add", this.yamlJenkinsFileName);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.gitRepo.toString());
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
            assertEquals(Result.SUCCESS, run.getResult());
            System.out.println(run.getLog());
        }
    }

    @Test
    void testWithSharedLibrary() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithLibrary.yml"), StandardCharsets.UTF_8);
        this.gitRepo.init();
        this.gitRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.gitRepo.git("add", this.yamlJenkinsFileName);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");

        this.gitRepo.mkdirs("vars");
        this.gitRepo.write("vars/customSteps.groovy", "def customStep(message)  {  echo \"${message}\" }");
        this.gitRepo.git("add", "vars/customSteps.groovy");
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithStep");

        GitSCMSource source = new GitSCMSource(this.gitRepo.toString());
        source.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        LibraryConfiguration sharedLibraryConfiguration =
                new LibraryConfiguration("customSharedLibrary", new SCMSourceRetriever(source));

        GlobalLibraries globalLibraries = GlobalLibraries.get();
        globalLibraries.setLibraries(List.of(sharedLibraryConfiguration));

        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(this.gitRepo.toString());
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
            assertEquals(Result.SUCCESS, run.getResult());
        }
    }
}
