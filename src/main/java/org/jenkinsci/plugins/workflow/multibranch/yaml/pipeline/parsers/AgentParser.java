package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTAgent;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTElement;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.AgentModel;

import java.util.LinkedHashMap;
import java.util.Optional;

public class AgentParser extends AbstractParser implements ParserInterface<AgentModel, ModelASTAgent> {

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
    public Optional<AgentModel> parse(ModelASTAgent modelAST) {
        return Optional.empty();
    }
}
