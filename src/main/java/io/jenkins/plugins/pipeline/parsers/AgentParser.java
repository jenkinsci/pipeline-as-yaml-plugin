package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.AgentModel;

import java.util.ArrayList;
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
            Object tempNode = this.getChildNodeAsObject(parentNode);
            if( tempNode instanceof String) {
                return Optional.of(new AgentModel((String) tempNode,new ArrayList<>()));
            }
            else {
                this.agentNode = (LinkedHashMap) tempNode;
                String agentType = this.getKey(this.agentNode);
                return Optional.of(new AgentModel(agentType, this.extractParameters(this.agentNode.get(agentType))));
            }
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
