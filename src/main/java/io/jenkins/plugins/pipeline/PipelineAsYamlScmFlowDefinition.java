package io.jenkins.plugins.pipeline;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Job;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import hudson.scm.SCMDescriptor;
import io.jenkins.plugins.pipeline.cps.PipelineCpsScmFlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowDefinitionDescriptor;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import java.util.Collection;
import java.util.List;

/**
 * SCM Binder class for {@link org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory}
 */
public class PipelineAsYamlScmFlowDefinition extends FlowDefinition {

    private String yamlJenkinsFilePath;
    private SCM yamlJenkinsFileScm;
    private boolean lightweight;

    /**
     * Constructor
     * @param yamlJenkinsFilePath Pipeline As Yaml File path in SCM
     * @param yamlJenkinsFileScm SCM Definition
     * @param lightweight LightWeight Checkout Flag
     */
    @DataBoundConstructor
    public PipelineAsYamlScmFlowDefinition(String yamlJenkinsFilePath, SCM yamlJenkinsFileScm, boolean lightweight) {
        this.yamlJenkinsFilePath = yamlJenkinsFilePath;
        this.yamlJenkinsFileScm = yamlJenkinsFileScm;
        this.lightweight = lightweight;
    }

    public String getYamlJenkinsFilePath() {
        return yamlJenkinsFilePath;
    }

    @DataBoundSetter
    public void setYamlJenkinsFilePath(String yamlJenkinsFilePath) {
        this.yamlJenkinsFilePath = yamlJenkinsFilePath;
    }

    public SCM getYamlJenkinsFileScm() {
        return yamlJenkinsFileScm;
    }

    @DataBoundSetter
    public void setYamlJenkinsFileScm(SCM yamlJenkinsFileScm) {
        this.yamlJenkinsFileScm = yamlJenkinsFileScm;
    }

    public boolean isLightweight() {
        return lightweight;
    }

    @DataBoundSetter
    public void setLightweight(boolean lightweight) {
        this.lightweight = lightweight;
    }

    @Override
    public FlowExecution create(FlowExecutionOwner handle, TaskListener listener, List<? extends Action> actions) throws Exception {
        PipelineCpsScmFlowDefinition pipelineCpsScmFlowDefinition = new PipelineCpsScmFlowDefinition(this.getYamlJenkinsFileScm(),this.getYamlJenkinsFilePath(), this.isLightweight());
        return pipelineCpsScmFlowDefinition.create(handle,listener,actions);
    }

    /**
     * Extension for {@link FlowDefinitionDescriptor}
     */
    @Extension
    public static class DescriptorImpl extends FlowDefinitionDescriptor {

        @Override
        public String getDisplayName() {
            return Messages.Project_ScmFlowDefinitionDisplayName();
        }

        public Collection<? extends SCMDescriptor<?>> getApplicableDescriptors() {
            StaplerRequest req = Stapler.getCurrentRequest();
            Job<?,?> job = req != null ? req.findAncestorObject(Job.class) : null;
            return SCM._for(job);
        }
    }
}
