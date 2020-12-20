package io.jenkins.plugins.pipeline;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import io.jenkins.plugins.pipeline.scm.ExtendedSCMBinder;
import io.jenkins.plugins.pipeline.scm.SCMSourceCriteriaForYamlFile;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.SCMSourceCriteria;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;

/**
 * Pipeline As YAML Implementation for {@link WorkflowBranchProjectFactory}
 */
public class PipelineAsYamlWorkflowBranchProjectFactory extends WorkflowBranchProjectFactory {

    private String yamlJenkinsFile = "Jenkinsfile.yaml";

    /**
     * Constructor
     * @param yamlJenkinsFile Path of the Pipeline As Yaml script file in SCM
     */
    @DataBoundConstructor
    public PipelineAsYamlWorkflowBranchProjectFactory(String yamlJenkinsFile) {
        this.yamlJenkinsFile = yamlJenkinsFile;
    }

    public String getYamlJenkinsFile() {
        return yamlJenkinsFile;
    }

    @DataBoundSetter
    public void setYamlJenkinsFile(String yamlJenkinsFile) {
        this.yamlJenkinsFile = yamlJenkinsFile;
    }

    @Override
    protected FlowDefinition createDefinition() {
        return new ExtendedSCMBinder(this.getYamlJenkinsFile());
    }

    @Override
    protected SCMSourceCriteria getSCMSourceCriteria(SCMSource source) {
        return ((probe, taskListener) -> SCMSourceCriteriaForYamlFile.matches(this.getYamlJenkinsFile(),probe,taskListener));
    }

    /**
     * Extension for {@link AbstractWorkflowBranchProjectFactoryDescriptor}
     */
    @Extension
    public static class DescriptorImpl extends AbstractWorkflowBranchProjectFactoryDescriptor {

        @Nonnull
        @Override
        public String getDisplayName() {
            return Messages.ProjectRecognizer_DisplayName();
        }
    }

    /**
     * Extension for {@link RunListener}
     */
    @Extension
    public static class RunListenerImpl extends RunListener<Run>
    {
        @Override
        public void onInitialize(Run run) {
            super.onInitialize(run);
        }
    }
}
