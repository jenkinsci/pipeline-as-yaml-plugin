package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hudson.model.Result;
import hudson.plugins.git.GitSCM;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
@WithGitSampleRepo
class PipelineAsYamlScmFlowDefinitionTest {

    private JenkinsRule jenkinsRule;
    private GitSampleRepoRule gitRepo;
    private final String yamlJenkinsFileName = "Jenkinsfile.yaml";

    @BeforeEach
    void setUp(JenkinsRule jenkinsRule, GitSampleRepoRule gitRepo) {
        this.jenkinsRule = jenkinsRule;
        this.gitRepo = gitRepo;
    }

    @Test
    void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineAllInOne.yml"), StandardCharsets.UTF_8);
        this.gitRepo.init();
        this.gitRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.gitRepo.git("add", this.yamlJenkinsFileName);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");
        WorkflowJob workflowJob = this.jenkinsRule.createProject(
                WorkflowJob.class, UUID.randomUUID().toString());
        workflowJob.setDefinition(new PipelineAsYamlScmFlowDefinition(
                this.yamlJenkinsFileName, new GitSCM(this.gitRepo.toString()), false));
        workflowJob.scheduleBuild2(0);
        jenkinsRule.waitUntilNoActivity();
        WorkflowRun workflowRun = workflowJob.getLastBuild();
        System.out.println(workflowRun.getLog());
        assertEquals(Result.SUCCESS, workflowRun.getResult());
    }

    @Test
    void testWithLibrary() throws Exception {

        this.gitRepo.init();
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

        String yamlJenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/job/pipelineTestWithLibrary.yml"), StandardCharsets.UTF_8);
        this.gitRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.gitRepo.git("add", this.yamlJenkinsFileName);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");
        WorkflowJob workflowJob = this.jenkinsRule.createProject(
                WorkflowJob.class, UUID.randomUUID().toString());
        workflowJob.setDefinition(new PipelineAsYamlScmFlowDefinition(
                this.yamlJenkinsFileName, new GitSCM(this.gitRepo.toString()), false));
        workflowJob.scheduleBuild2(0);
        jenkinsRule.waitUntilNoActivity();
        WorkflowRun workflowRun = workflowJob.getLastBuild();
        System.out.println(workflowRun.getLog());
        this.jenkinsRule.assertLogContains("customEcho", workflowRun);
        assertEquals(Result.SUCCESS, workflowRun.getResult());
    }
}
