package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTAgent;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTClosureMap;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTElement;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.AgentModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.KeyValueModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AgentParser extends AbstractParser implements ParserInterface<AgentModel> {

    private LinkedHashMap agentNode;

    public AgentParser(){
        this.yamlNodeName = AgentModel.directive;
    }

    @Override
    public Optional<AgentModel> parse(LinkedHashMap parentNode) {
        try {
            this.agentNode = this.getChildNodeAsLinkedHashMap(parentNode);
            String agentType = this.getKey(this.agentNode);
            return Optional.of(new AgentModel(agentType, this.extractParameters(this.agentNode.get(agentType))));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AgentModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        ModelASTAgent modelASTAgent = modelASTPipelineDef.getAgent();
        String agentType = modelASTAgent.getAgentType().getKey();
        List<KeyValueModel> agentParameters = this.convert(modelASTAgent);
        AgentModel agentModel = new AgentModel(agentType,agentParameters);
        return Optional.of(agentModel);
    }


}
