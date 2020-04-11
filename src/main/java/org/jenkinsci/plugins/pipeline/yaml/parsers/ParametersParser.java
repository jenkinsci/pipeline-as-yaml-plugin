package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ParametersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ParametersParser extends AbstractParser implements ParserInterface<ParametersModel> {

    private List parametersNode;
    private LinkedHashMap parentNode;

    public ParametersParser(LinkedHashMap parentNode){
        this.yamlNodeName = "parameters";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ParametersModel> parse() {
        try {
            this.parametersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new ParametersModel(this.parametersNode));
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
