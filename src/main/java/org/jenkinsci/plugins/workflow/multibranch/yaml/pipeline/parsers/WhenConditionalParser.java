package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.WhenConditionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class WhenConditionalParser extends AbstractParser implements ParserInterface<WhenConditionModel> {

    private LinkedHashMap parentNode;

    public WhenConditionalParser(LinkedHashMap parentNode){
        this.parentNode = parentNode;
    }

    @Override
    public Optional<WhenConditionModel> parse() {
        try {
            String conditionKey = this.getKey(parentNode);
            Object conditionObject = this.getValue(parentNode, conditionKey);
            if( conditionObject instanceof  List) {
                return Optional.of(new WhenConditionModel(conditionKey, (List<String>) conditionObject));
            }
            else if (conditionObject instanceof  LinkedHashMap) {
                return Optional.of(new WhenConditionModel(conditionKey,new WhenConditionalParser((LinkedHashMap) conditionObject).parse()));
            }
            else {
                throw new PipelineAsYamlUnknownTypeException(conditionObject.getClass().toString());
            }
        }
        catch (PipelineAsYamlException e) {
            return Optional.empty();
        }

    }
}
