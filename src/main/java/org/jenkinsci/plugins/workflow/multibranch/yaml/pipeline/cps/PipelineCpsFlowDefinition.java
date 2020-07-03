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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Extended CpsFlowDefinition for Pipeline As Yaml from Script Editor
 */
public class PipelineCpsFlowDefinition extends CpsFlowDefinition {

    /**
     * Constructor
     * @param script Pipeline As Yaml SCript
     * @param sandbox Sandox Flag
     */
    public PipelineCpsFlowDefinition(String script, boolean sandbox) {
        super(script, sandbox);
    }

    @Override
    public CpsFlowExecution create(FlowExecutionOwner owner, TaskListener listener, List<? extends Action> actions) throws IOException {
        CpsFlowExecution cpsFlowExecution =  super.create(owner, listener, actions);
        String yamlJenkinsFileContent = cpsFlowExecution.getScript();
        if( yamlJenkinsFileContent == null || yamlJenkinsFileContent == "") {
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
