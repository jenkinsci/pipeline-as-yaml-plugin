package io.jenkins.plugins.pipeline;

import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;
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

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class PipelineAsYamlScriptFlowDefinitionTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Rule
    public GitSampleRepoRule sourceCodeRepo = new GitSampleRepoRule();

    @Rule
    public GitSampleRepoRule libraryCodeRepo = new GitSampleRepoRule();

    private String yamlJenkinsFileName = "Jenkinsfile.yaml";
    private String[] scmBranches = {"feature","hotfix"};

    @Test
    public void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/job/pipelineAllInOne.yml"));
        WorkflowJob workflowJob = this.jenkinsRule.createProject(WorkflowJob.class, UUID.randomUUID().toString());
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


        LibraryConfiguration sharedLibraryConfiguration = new LibraryConfiguration("customSharedLibrary", new SCMSourceRetriever(
                new GitSCMSource(null, this.libraryCodeRepo.toString(),"","*","",false)
        ));

        GlobalLibraries globalLibraries = GlobalLibraries.get();
        globalLibraries.setLibraries(Arrays.asList(sharedLibraryConfiguration));

        String yamlJenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/job/pipelineTestWithLibrary.yml"));
        WorkflowJob workflowJob = this.jenkinsRule.createProject(WorkflowJob.class, UUID.randomUUID().toString());
        workflowJob.setDefinition(new PipelineAsYamlScriptFlowDefinition(yamlJenkinsFileContent,true));
        workflowJob.scheduleBuild2(0);
        jenkinsRule.waitUntilNoActivity();
        WorkflowRun workflowRun = workflowJob.getLastBuild();
        System.out.println(workflowRun.getLog());
        this.jenkinsRule.assertLogContains("customEcho", workflowRun);
        Assert.assertEquals("SUCCESS", workflowRun.getResult().toString());
    }
}
