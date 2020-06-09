package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.AgentModel;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Parser for {@link AgentModel}
 */
public class AgentParser extends AbstractParser implements ParserInterface<AgentModel> {

    private LinkedHashMap agentNode;
    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public AgentParser(LinkedHashMap parentNode){
        this.yamlNodeName = AgentModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<AgentModel> parse() {
        try {
            this.agentNode = this.getChildNodeAsLinkedHashMap(parentNode);
            String agentType = this.getKey(this.agentNode);
            return Optional.of(new AgentModel(agentType, this.extractParameters(this.agentNode.get(agentType))));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
