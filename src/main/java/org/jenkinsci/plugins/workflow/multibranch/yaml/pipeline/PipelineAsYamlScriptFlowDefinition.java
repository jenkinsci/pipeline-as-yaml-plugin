package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowDefinitionDescriptor;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.cps.PipelineCpsFlowDefinition;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.List;

/**
 * SCM Binder class for {@link org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory}
 */
public class PipelineAsYamlScriptFlowDefinition extends FlowDefinition {

    private String yamlJenkinsScript;
    private boolean sandbox;

    @DataBoundConstructor
    public PipelineAsYamlScriptFlowDefinition(String yamlJenkinsScript, boolean sandbox) {
        this.yamlJenkinsScript = yamlJenkinsScript;
        this.sandbox = sandbox;
    }

    public String getYamlJenkinsScript() {
        return yamlJenkinsScript;
    }

    @DataBoundSetter
    public void setYamlJenkinsScript(String yamlJenkinsScript) {
        this.yamlJenkinsScript = yamlJenkinsScript;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    @DataBoundSetter
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    @Override
    public FlowExecution create(FlowExecutionOwner handle, TaskListener listener, List<? extends Action> actions) throws Exception {
        PipelineCpsFlowDefinition pipelineCpsFlowDefinition = new PipelineCpsFlowDefinition(this.getYamlJenkinsScript(), this.isSandbox());
        return pipelineCpsFlowDefinition.create(handle,listener,actions);
    }

    @Extension
    public static class DescriptorImpl extends FlowDefinitionDescriptor {

        @Override
        public String getDisplayName() {
            return Messages.Project_ScriptFlowDefinitionDisplayName();
        }

    }
}
