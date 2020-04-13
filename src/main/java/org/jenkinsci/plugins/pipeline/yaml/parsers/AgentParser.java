package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Optional;

public class AgentParser extends AbstractParser implements ParserInterface<AgentModel> {

    private LinkedHashMap agentNode;
    private LinkedHashMap parentNode;

    public AgentParser(LinkedHashMap parentNode){
        this.yamlNodeName = AgentModel.directive;
        this.yaml = new Yaml();
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
