package io.jenkins.plugins.pipeline.scm;

import hudson.model.Action;
import hudson.model.Queue;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlRuntimeException;
import jenkins.branch.Branch;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jenkinsci.plugins.workflow.multibranch.BranchJobProperty;
import io.jenkins.plugins.pipeline.cps.PipelineCpsScmFlowDefinition;

import java.util.List;

/**
 * SCM Binder class for {@link org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory}
 */
public class ExtendedSCMBinder extends FlowDefinition {

    private String yamlJenkinsfile;

    /**
     * @param yamlJenkinsfile
     */
    public ExtendedSCMBinder(String yamlJenkinsfile) {
        this.yamlJenkinsfile = yamlJenkinsfile;
    }

    @Override
    public FlowExecution create(FlowExecutionOwner handle, TaskListener listener, List<? extends Action> actions) throws Exception {
        Queue.Executable executable = handle.getExecutable();
        if(!(executable instanceof WorkflowRun)) {
            throw new PipelineAsYamlRuntimeException("Executable is not instance of WorkflowRun");
        }
        WorkflowRun run = (WorkflowRun) executable;
        WorkflowJob workflowJob = run.getParent();
        BranchJobProperty branchJobProperty = workflowJob.getProperty(BranchJobProperty.class);
        if( branchJobProperty == null ) {
            throw new PipelineAsYamlRuntimeException("BranchJobProperty can not be null");
        }
        Branch branch = branchJobProperty.getBranch();
        if( branch == null) {
            throw new PipelineAsYamlRuntimeException("Branch can not be null");
        }
        SCM scm = branch.getScm();
        PipelineCpsScmFlowDefinition pipelineCpsScmFlowDefinition = new PipelineCpsScmFlowDefinition(scm,this.yamlJenkinsfile);
        return pipelineCpsScmFlowDefinition.create(handle,listener,actions);
    }

}
