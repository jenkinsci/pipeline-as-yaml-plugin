package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.OptionsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OptionsParser extends AbstractParser implements ParserInterface<OptionsModel> {

    private List optionsNode;
    private LinkedHashMap parentNode;

    public OptionsParser(LinkedHashMap parentNode){
        this.yamlNodeName = OptionsModel.directive;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<OptionsModel> parse() {
        try {
            this.optionsNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new OptionsModel(this.optionsNode));
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
