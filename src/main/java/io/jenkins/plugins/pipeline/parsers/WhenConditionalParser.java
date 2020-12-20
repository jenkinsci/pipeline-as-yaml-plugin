package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.WhenConditionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link WhenConditionModel}
 */
public class WhenConditionalParser extends AbstractParser implements ParserInterface<WhenConditionModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
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
