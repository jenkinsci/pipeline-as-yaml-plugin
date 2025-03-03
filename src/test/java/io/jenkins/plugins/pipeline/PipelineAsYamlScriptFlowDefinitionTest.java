package io.jenkins.plugins.pipeline;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class PipelineAsYamlScriptFlowDefinitionTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Rule
    public GitSampleRepoRule sourceCodeRepo = new GitSampleRepoRule();

    @Rule
    public GitSampleRepoRule libraryCodeRepo = new GitSampleRepoRule();

    private String yamlJenkinsFileName = "Jenkinsfile.yaml";
    private String[] scmBranches = {"feature", "hotfix"};

    @Test
    public void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineAllInOne.yml"), StandardCharsets.UTF_8);
        WorkflowJob workflowJob = this.jenkinsRule.createProject(
                WorkflowJob.class, UUID.randomUUID().toString());
        workflowJob.setDefinition(new PipelineAsYamlScriptFlowDefinition(yamlJenkinsFileContent, true));
        workflowJob.scheduleBuild2(0);
        jenkinsRule.waitUntilNoActivity();
        WorkflowRun workflowRun = workflowJob.getLastBuild();
        System.out.println(workflowRun.getLog());
        Assert.assertEquals("SUCCESS", workflowRun.getResult().toString());
    }

    @Test
    public void testWithLibrary() throws Exception {

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

        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithLibrary.yml"), StandardCharsets.UTF_8);
        WorkflowJob workflowJob = this.jenkinsRule.createProject(
                WorkflowJob.class, UUID.randomUUID().toString());
        workflowJob.setDefinition(new PipelineAsYamlScriptFlowDefinition(yamlJenkinsFileContent, true));
        workflowJob.scheduleBuild2(0);
        jenkinsRule.waitUntilNoActivity();
        WorkflowRun workflowRun = workflowJob.getLastBuild();
        System.out.println(workflowRun.getLog());
        this.jenkinsRule.assertLogContains("customEcho", workflowRun);
        Assert.assertEquals("SUCCESS", workflowRun.getResult().toString());
    }
}
