package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.OptionsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OptionsParser extends AbstractParser implements ParserInterface<OptionsModel> {

    private List optionsNode;
    private LinkedHashMap parentNode;

    public OptionsParser(LinkedHashMap parentNode){
        this.yamlNodeName = OptionsModel.directive;
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
