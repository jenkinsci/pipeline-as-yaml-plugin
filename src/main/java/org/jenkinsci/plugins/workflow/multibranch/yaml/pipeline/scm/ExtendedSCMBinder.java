package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.scm;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Queue;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import jenkins.branch.Branch;
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
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlRuntimeException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;

import java.util.List;
import java.util.Optional;

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
        CpsFlowExecution cpsFlowExecution =  new CpsScmFlowDefinition(scm, this.yamlJenkinsfile).create(handle, listener, actions);
        String yamlJenkinsFileContent = cpsFlowExecution.getScript();
        if( yamlJenkinsFileContent == null) {
            throw new PipelineAsYamlRuntimeException("yamlJenkinsFileContent can not be null");
        }
        PipelineParser pipelineParser = new PipelineParser(yamlJenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        if(!pipelineModel.isPresent()) {
            throw new PipelineAsYamlRuntimeException("PipelineModel is not present");
        }
        String jenkinsFileContent = pipelineModel.get().toPrettyGroovy();
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
