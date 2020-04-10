package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlParseException;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlUnexpectedNodeNumber;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class AgentParser extends AbstractParser implements ParserInterface<AgentModel> {

    private LinkedHashMap agentNode;
    private LinkedHashMap pipelineNode;

    public AgentParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "agent";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    public AgentParser(LinkedHashMap pipelineNode, Boolean nodeRequired){
        this.yamlNodeName = "agent";
        this.nodeRequired = nodeRequired;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public AgentModel parse() {
        this.agentNode = this.getChildNodeAsLinkedHashMap(pipelineNode);
        String agentType = this.getKey(this.agentNode);
        return new AgentModel(agentType, this.extractParameters(this.agentNode.get(agentType)));
    }
}
