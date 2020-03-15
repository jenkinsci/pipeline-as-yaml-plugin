package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.EnvironmentModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;

public class EnvironmentParser extends AbstractParser implements ParserInterface<EnvironmentModel> {

    private LinkedHashMap environmentNode;
    private LinkedHashMap pipelineNode;

    public EnvironmentParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "environment";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public EnvironmentModel parse() {
        this.environmentNode = this.getChildNode(pipelineNode);
        return new EnvironmentModel(this.extractParameters(this.environmentNode));
    }
}
