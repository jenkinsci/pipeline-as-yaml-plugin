package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.cps;

import hudson.model.Action;
import hudson.model.TaskListener;
import hudson.scm.SCM;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlRuntimeException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;

import java.util.List;
import java.util.Optional;

/**
 * Extended CpsFlowDefinition for Pipeline As Yaml from SCM in Pipeline Job
 */
public class PipelineCpsScmFlowDefinition extends CpsScmFlowDefinition {

    /**
     * Constructor
     * @param scm SCM Definition
     * @param scriptPath Path of the yaml file in SVM
     * @param lightweight LightWeight Checkout Flag
     */
    public PipelineCpsScmFlowDefinition(SCM scm, String scriptPath, boolean lightweight) {
        super(scm, scriptPath);
        this.setLightweight(lightweight);
    }

    /**
     * Constructor
     * @param scm SCM Definition
     * @param scriptPath Path of the yaml file in SVM
     */
    public PipelineCpsScmFlowDefinition(SCM scm, String scriptPath) {
        super(scm, scriptPath);
    }

    @Override
    public CpsFlowExecution create(FlowExecutionOwner owner, TaskListener listener, List<? extends Action> actions) throws Exception {
        CpsFlowExecution cpsFlowExecution =  super.create(owner, listener, actions);
        String yamlJenkinsFileContent = cpsFlowExecution.getScript();
        if( yamlJenkinsFileContent == null || yamlJenkinsFileContent.equals("")) {
            throw new PipelineAsYamlRuntimeException("yamlJenkinsFileContent can not be null/empty");
        }
        PipelineParser pipelineParser = new PipelineParser(yamlJenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        if(!pipelineModel.isPresent()) {
            throw new PipelineAsYamlRuntimeException("PipelineModel is not present");
        }
        String jenkinsFileContent = pipelineModel.get().toPrettyGroovy();
        return new CpsFlowDefinition(jenkinsFileContent,cpsFlowExecution.isSandbox()).create(owner,listener, actions);
    }
}
