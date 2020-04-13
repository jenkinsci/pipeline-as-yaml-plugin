package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.TriggersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class TriggersParser extends AbstractParser implements ParserInterface<TriggersModel> {

    private List triggersNode;
    private LinkedHashMap parentNode;

    public TriggersParser(LinkedHashMap parentNode){
        this.yamlNodeName = TriggersModel.directive;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<TriggersModel> parse() {
        try {
            this.triggersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new TriggersModel(this.triggersNode));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
