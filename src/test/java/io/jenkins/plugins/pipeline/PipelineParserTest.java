package io.jenkins.plugins.pipeline;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.CredentialsStore;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.plugins.credentials.domains.Domain;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import hudson.ExtensionList;
import hudson.model.Result;
import hudson.plugins.git.GitSCM;
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

    private SystemCredentialsProvider.ProviderImpl system;
    private CredentialsStore systemStore;
    private String pipelineFile = "Jenkinsfile";
    private String pipelineAsYamlFileContent;

    @Parameterized.Parameters
    public static Iterable<String> data() {
        return Arrays.asList(
                "src/test/resources/pipeline/pipelineAllinOne.yml"
        );
    }

    @Before
    public void setup() throws IOException {
        system = ExtensionList.lookup(CredentialsProvider.class).get(SystemCredentialsProvider.ProviderImpl.class);
        systemStore = system.getStore(jenkins.getInstance());
        systemStore.addCredentials(Domain.global(),
                new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,"test-credentials","","username","password"));

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

    @Test
    public void pipelineTestWithScm() throws Exception {
        this.initSourceCodeRepo(this.pipelineAsYamlFileContent);
        WorkflowJob workflowJob = this.jenkins.createProject(WorkflowJob.class);
        workflowJob.setDefinition(new PipelineAsYamlScmFlowDefinition(
                this.pipelineFile,
                new GitSCM(this.gitRepo.getRoot().getAbsolutePath()),false));
        workflowJob.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Assert.assertEquals("SUCCESS", workflowJob.getBuilds().getLastBuild().getResult().toString());

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
