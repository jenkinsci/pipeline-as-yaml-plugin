package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.SCMSourceCriteria;
import org.jenkinsci.plugins.workflow.cps.SnippetizerLink;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.scm.ExtendedSCMBinder;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.scm.SCMSourceCriteriaForYamlFile;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;

public class PipelineAsYamlWorkflowBranchProjectFactory extends WorkflowBranchProjectFactory {

    private String yamlJenkinsFile = "Jenkinsfile.yaml";

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
        return new ExtendedSCMBinder(this.yamlJenkinsFile);
    }

    @Override
    protected SCMSourceCriteria getSCMSourceCriteria(SCMSource source) {
        return ((probe, taskListener) -> SCMSourceCriteriaForYamlFile.matches(this.yamlJenkinsFile,probe,taskListener));
    }

    @Extension
    public static class DescriptorImpl extends AbstractWorkflowBranchProjectFactoryDescriptor {

        @Nonnull
        @Override
        public String getDisplayName() {
            return Messages.ProjectRecognizer_DisplayName();
        }
    }


    @Extension
    public static class RunListenerImpl extends RunListener<Run>
    {
        @Override
        public void onInitialize(Run run) {
            super.onInitialize(run);
        }
    }
}
