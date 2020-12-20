package io.jenkins.plugins.pipeline;

import hudson.model.Result;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import jenkins.branch.BranchSource;
import jenkins.plugins.git.GitSCMSource;
import jenkins.plugins.git.GitSampleRepoRule;
import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RunWith(Parameterized.class)
public class PipelineParserTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Rule
    public GitSampleRepoRule gitRepo = new GitSampleRepoRule();

    private String pipelineFile = "Jenkinsfile";
    private String pipelineAsYamlFileContent;

    @Parameterized.Parameters
    public static Iterable<String> data() {
        return Arrays.asList(
                "src/test/resources/pipeline/pipelineAllinOne.yml"
        );
    }

    @Before
    public void setup() {
    }

    public PipelineParserTest(String filePath) throws IOException {
        this.pipelineAsYamlFileContent = FileUtils.readFileToString(new File(filePath));
    }

    @Test
    public void pipeline1() throws Exception {
        PipelineParser pipelineParser  = new PipelineParser(this.pipelineAsYamlFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parseAndValidate();
        Assert.assertTrue(pipelineModel.isPresent());
        String prettyGroovyScript = pipelineModel.get().toPrettyGroovy();
        System.out.println(prettyGroovyScript);
        WorkflowMultiBranchProject workflowMultiBranchProject = this.createWorkflowMultiBranchPipelineJob(prettyGroovyScript);
        this.checkPipelineBuild(workflowMultiBranchProject);
    }

    private WorkflowMultiBranchProject createWorkflowMultiBranchPipelineJob(String pipelineScript) throws Exception {
        this.initSourceCodeRepo(pipelineScript);
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(new GitSCMSource(null, this.gitRepo.toString(), "", "*", "", false)));
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Assert.assertEquals(1, workflowMultiBranchProject.getItems().size());
        return workflowMultiBranchProject;
    }

    private void initSourceCodeRepo(String pipelineScript) throws Exception {
        this.gitRepo.init();
        this.gitRepo.write(this.pipelineFile, pipelineScript);
        this.gitRepo.git("add", this.pipelineFile);
        this.gitRepo.git("commit", "--all", "--message=InitRepoWithFile");
    }

    private void checkPipelineBuild(WorkflowMultiBranchProject workflowMultiBranchProject) throws IOException {
        WorkflowJob workflowJob = workflowMultiBranchProject.getJob("master");
        WorkflowRun run = workflowJob.getLastBuild();
        System.out.println(run.getLog());
        Result result = run.getResult();
        Assert.assertEquals("SUCCESS",result.toString());
    }   
}
