package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ParametersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;

public class ParametersParser extends AbstractParser implements ParserInterface<ParametersModel> {

    private List parametersNode;
    private LinkedHashMap pipelineNode;

    public ParametersParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "parameters";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public ParametersModel parse() {
        this.parametersNode = (List) this.getChildNodeAsList(pipelineNode);
        return new ParametersModel(this.parametersNode);
    }
}
