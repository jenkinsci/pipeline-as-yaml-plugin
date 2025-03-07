package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
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
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
@WithGitSampleRepo
class PipelineParserTest {

    private JenkinsRule jenkins;
    private GitSampleRepoRule gitRepo;

    private SystemCredentialsProvider.ProviderImpl system;
    private CredentialsStore systemStore;
    private final String pipelineFile = "Jenkinsfile";
    private String pipelineAsYamlFileContent;

    @BeforeEach
    void setUp(JenkinsRule jenkinsRule, GitSampleRepoRule gitRepo) throws Exception {
        this.jenkins = jenkinsRule;
        this.gitRepo = gitRepo;
        system = ExtensionList.lookup(CredentialsProvider.class).get(SystemCredentialsProvider.ProviderImpl.class);
        systemStore = system.getStore(jenkins.getInstance());
        systemStore.addCredentials(
                Domain.global(),
                new UsernamePasswordCredentialsImpl(
                        CredentialsScope.GLOBAL, "test-credentials", "", "username", "password"));
        pipelineAsYamlFileContent = FileUtils.readFileToString(
                new File("src/test/resources/pipeline/pipelineAllinOne.yml"), StandardCharsets.UTF_8);
    }

    @Test
    void pipeline1() throws Exception {
        PipelineParser pipelineParser = new PipelineParser(this.pipelineAsYamlFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parseAndValidate();
        assertTrue(pipelineModel.isPresent());
        String prettyGroovyScript = pipelineModel.get().toPrettyGroovy();
        System.out.println(prettyGroovyScript);
        WorkflowMultiBranchProject workflowMultiBranchProject =
                this.createWorkflowMultiBranchPipelineJob(prettyGroovyScript);
        this.checkPipelineBuild(workflowMultiBranchProject);
    }

    @Test
    void pipelineTestWithScm() throws Exception {
        this.initSourceCodeRepo(this.pipelineAsYamlFileContent);
        WorkflowJob workflowJob = this.jenkins.createProject(WorkflowJob.class);
        workflowJob.setDefinition(new PipelineAsYamlScmFlowDefinition(
                this.pipelineFile, new GitSCM(this.gitRepo.getRoot().getAbsolutePath()), false));
        workflowJob.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        assertEquals(Result.SUCCESS, workflowJob.getBuilds().getLastBuild().getResult());
    }

    private WorkflowMultiBranchProject createWorkflowMultiBranchPipelineJob(String pipelineScript) throws Exception {
        this.initSourceCodeRepo(pipelineScript);
        GitSCMSource source = new GitSCMSource(this.gitRepo.toString());
        source.setTraits(List.of(new BranchDiscoveryTrait(), new WildcardSCMHeadFilterTrait("*", "")));
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(
                WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(source));
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        assertEquals(1, workflowMultiBranchProject.getItems().size());
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
        assertEquals(Result.SUCCESS, result);
    }
}
