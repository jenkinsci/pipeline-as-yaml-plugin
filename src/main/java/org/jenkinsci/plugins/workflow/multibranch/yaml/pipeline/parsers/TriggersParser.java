package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.TriggersModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class TriggersParser extends AbstractParser implements ParserInterface<TriggersModel> {

    private List triggersNode;
    private LinkedHashMap parentNode;

    public TriggersParser(LinkedHashMap parentNode){
        this.yamlNodeName = TriggersModel.directive;
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
