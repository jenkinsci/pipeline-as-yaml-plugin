package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlParseException;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlUnexpectedNodeNumber;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Set;

public class AgentParser extends AbstractParser implements ParserInterface<AgentModel> {

    private LinkedHashMap agentNode;
    private LinkedHashMap pipelineNode;

    public AgentParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "agent";
        this.nodeRequired = true;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public AgentModel parse() {
        this.agentNode = (LinkedHashMap) this.getChildNode(pipelineNode);
        this.agentNode = this.checkExpectedSite(this.agentNode);
        String agentType = this.getKey(this.agentNode);
        return new AgentModel(agentType, this.extractParameters(this.agentNode.get(agentType)));
    }
}
