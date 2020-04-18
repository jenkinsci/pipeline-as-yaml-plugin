package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.scm;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Queue;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import jenkins.branch.Branch;
import jenkins.scm.api.SCMFileSystem;
import jenkins.scm.api.SCMSource;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowDefinitionDescriptor;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.multibranch.BranchJobProperty;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;

import java.util.List;

public class ExtenderSCMBinder extends FlowDefinition {

    private String yamlJenkinsfile;

    public ExtenderSCMBinder(String yamlJenkinsfile) {
        this.yamlJenkinsfile = yamlJenkinsfile;
    }


    @Override
    public FlowExecution create(FlowExecutionOwner handle, TaskListener listener, List<? extends Action> actions) throws Exception {
        WorkflowRun run = (WorkflowRun) handle.getExecutable();
        WorkflowJob workflowJob = run.getParent();
        BranchJobProperty branchJobProperty = workflowJob.getProperty(BranchJobProperty.class);
        Branch branch = branchJobProperty.getBranch();
        SCM scm = branch.getScm();
        CpsFlowExecution cpsFlowExecution =  new CpsScmFlowDefinition(scm, this.yamlJenkinsfile).create(handle, listener, actions);
        String yamlJenkinsFileContent = cpsFlowExecution.getScript();
        PipelineParser pipelineParser = new PipelineParser(yamlJenkinsFileContent);
        String jenkinsFileContent = pipelineParser.parse().get().toPrettyGroovy();
        return new CpsFlowDefinition(jenkinsFileContent,cpsFlowExecution.isSandbox()).create(handle,listener, actions);
    }

    @Extension
    public static class DescriptorImpl extends FlowDefinitionDescriptor {

        @Override
        public String getDisplayName() {
            return "Pipeline from Remote Jenkins File Plugin";
        }
    }
}
