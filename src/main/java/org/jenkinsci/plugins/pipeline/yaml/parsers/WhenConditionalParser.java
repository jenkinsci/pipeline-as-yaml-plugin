package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.WhenConditionModel;
import org.jenkinsci.plugins.pipeline.yaml.models.WhenModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class WhenConditionalParser extends AbstractParser implements ParserInterface<WhenConditionModel> {

    private LinkedHashMap parentNode;

    public WhenConditionalParser(LinkedHashMap parentNode){
        this.yaml = new Yaml();
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
            return Optional.empty();
        }
        catch (PipelineAsYamlKeyEmptyException e) {
            return Optional.empty();
        }

    }
}
