package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

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
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class PipelineAsYamlWorkflowBranchProjectFactoryTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Rule
    public GitSampleRepoRule sourceCodeRepo = new GitSampleRepoRule();

    private String yamlJenkinsFileName = "yamlJenkinsfile.yaml";
    private String[] scmBranches = {"feature","hotfix"};

    @Before
    public void setup() {

    }

    @Test
    public void testWithBranches() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/job/pipelineTestWithBranches.yml"));
        this.sourceCodeRepo.init();
        this.sourceCodeRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.sourceCodeRepo.git("add", this.yamlJenkinsFileName);
        this.sourceCodeRepo.git("commit", "--all", "--message=InitRepoWithFile");
        for(String branch : this.scmBranches) {
            this.sourceCodeRepo.git("checkout","-b",branch,"master");
        }
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(null, this.sourceCodeRepo.toString(), "", "*", "", false);
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory = new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        Assert.assertEquals(this.scmBranches.length + 1, items.size());
        for(WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            String testString = "test-output-"+job.getName();
            System.out.println(run.getLog());
        }
    }

    @Test
    public void testAllInOne() throws Exception {
        String yamlJenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/job/pipelineAllInOne.yml"));
        this.sourceCodeRepo.init();
        this.sourceCodeRepo.write(this.yamlJenkinsFileName, yamlJenkinsFileContent);
        this.sourceCodeRepo.git("add", this.yamlJenkinsFileName);
        this.sourceCodeRepo.git("commit", "--all", "--message=InitRepoWithFile");
        WorkflowMultiBranchProject workflowMultiBranchProject = this.jenkins.createProject(WorkflowMultiBranchProject.class, UUID.randomUUID().toString());
        GitSCMSource sourceCodeRepoSCMSource = new GitSCMSource(null, this.sourceCodeRepo.toString(), "", "*", "", false);
        workflowMultiBranchProject.getSourcesList().add(new BranchSource(sourceCodeRepoSCMSource));
        PipelineAsYamlWorkflowBranchProjectFactory pipelineAsYamlWorkflowBranchProjectFactory = new PipelineAsYamlWorkflowBranchProjectFactory(this.yamlJenkinsFileName);
        workflowMultiBranchProject.setProjectFactory(pipelineAsYamlWorkflowBranchProjectFactory);
        workflowMultiBranchProject.scheduleBuild2(0);
        this.jenkins.waitUntilNoActivity();
        Collection<WorkflowJob> items = workflowMultiBranchProject.getItems();
        for(WorkflowJob job : items) {
            WorkflowRun run = job.getLastBuild();
            Assert.assertEquals(run.getResult().toString(),"SUCCESS");
        }
    }

}
