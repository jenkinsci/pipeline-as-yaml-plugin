package io.jenkins.plugins.pipeline.cps;

import hudson.model.Action;
import hudson.model.TaskListener;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlRuntimeException;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionOwner;

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
        if( StringUtils.isBlank(yamlJenkinsFileContent) ) {
            throw new PipelineAsYamlRuntimeException("Jenkinsfile YAML cannot be blank");
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
