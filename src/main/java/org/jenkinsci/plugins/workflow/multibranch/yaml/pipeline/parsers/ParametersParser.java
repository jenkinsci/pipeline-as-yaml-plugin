package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ParametersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ParametersParser extends AbstractParser implements ParserInterface<ParametersModel> {

    private List parametersNode;
    private LinkedHashMap parentNode;

    public ParametersParser(LinkedHashMap parentNode){
        this.yamlNodeName = ParametersModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ParametersModel> parse() {
        try {
            this.parametersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new ParametersModel(this.parametersNode));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
