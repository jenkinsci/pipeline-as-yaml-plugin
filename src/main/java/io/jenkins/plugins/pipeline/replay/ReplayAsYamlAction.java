package io.jenkins.plugins.pipeline.replay;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Job;
import hudson.model.Run;
import hudson.security.Permission;
import hudson.security.PermissionScope;
import io.jenkins.plugins.pipeline.PipelineAsYamlScmFlowDefinition;
import io.jenkins.plugins.pipeline.PipelineAsYamlScriptFlowDefinition;
import jenkins.model.Jenkins;
import jenkins.model.TransientActionFactory;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.cps.replay.ReplayAction;
import org.jenkinsci.plugins.workflow.flow.FlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Collection;
import java.util.Collections;

public class ReplayAsYamlAction implements Action {

    public final Run run;
    private String pipelineScript = "";
    private boolean sandbox = true;

    @DataBoundConstructor
    public ReplayAsYamlAction(Run run) {
        this.run = run;
        this.init();
    }

    private void init(){
        if( this.run instanceof WorkflowRun) {
            WorkflowRun workflowRun = (WorkflowRun) run;
            CpsFlowExecution cpsFlowExecution = (CpsFlowExecution) workflowRun.getExecution();
            this.sandbox = cpsFlowExecution.isSandbox();
            this.pipelineScript = cpsFlowExecution.getScript();
        }
    }

    @Override
    public String getIconFileName() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "Replay As YAML";
    }

    @Override
    public String getUrlName() {
        return "replayAsYaml";
    }

    /** Method for Jelly **/
    public Run getOwner(){
        return this.run;
    }

    public String getPageHeader(){
        return "Replay #" + this.run.getNumber() + " as YAML";
    }

    /**
     * Check if the CPSFlowExecution is Sandbox
     * @return Boolean
     */
    public boolean isSandbox(){
        return sandbox;
    }

    /**
     * Check if this action is enabled or not
     * @return Boolean
     */
    public boolean isEnabled(){
        //Check REPLAY Permission
        if( !this.run.hasPermission(ReplayAction.REPLAY))
            return false;
        //Check BUILD Permission
        if( !this.run.hasPermission(Item.BUILD))
            return false;
        if( !this.isSandbox())
            if( ! Jenkins.get().hasPermission(Jenkins.RUN_SCRIPTS))
                return false;
        return true;
    }

    public String getPipelineScript(){
        return this.pipelineScript;
    }

    @Extension
    public static class ReplayAsYamlActionFactory extends TransientActionFactory<Run> {

        @Override
        public Class<Run> type() {
            return Run.class;
        }

        @Override
        public Collection<? extends Action> createFor(Run run) {
            // Check if the parent job is Workflow Job and using PipelineAsYamlCps
            // Not return empty list to hide Action
            Job parentJob = run.getParent();
            if( parentJob instanceof WorkflowJob) {
                WorkflowJob workflowJob = (WorkflowJob) parentJob;
                FlowDefinition flowDefinition = workflowJob.getDefinition();
                if( flowDefinition instanceof PipelineAsYamlScriptFlowDefinition || flowDefinition instanceof PipelineAsYamlScmFlowDefinition) {
                    return Collections.singleton(new ReplayAsYamlAction(run));
                }
            }
            return Collections.emptyList();
        }
    }
}
