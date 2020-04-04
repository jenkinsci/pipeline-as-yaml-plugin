package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ParametersModel;
import org.jenkinsci.plugins.pipeline.yaml.models.TriggersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;

public class TriggersParser extends AbstractParser implements ParserInterface<TriggersModel> {

    private List triggersNode;
    private LinkedHashMap pipelineNode;

    public TriggersParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "triggers";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public TriggersModel parse() {
        this.triggersNode = (List) this.getChildNodeAsList(pipelineNode);
        return new TriggersModel(this.triggersNode);
    }
}
