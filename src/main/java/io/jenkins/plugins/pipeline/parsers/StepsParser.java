package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.StepsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link StepsModel}
 */
public class StepsParser extends AbstractParser implements ParserInterface<StepsModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public StepsParser(LinkedHashMap parentNode) {
        this.yamlNodeName = StepsModel.directive;
        this.parentNode = parentNode;
    }

    /**
     * @param stepsList List of steps
     */
    public StepsParser(List stepsList) {
        this.yamlNodeName = StepsModel.directive;
        LinkedHashMap tempLinkedHashMap = new LinkedHashMap();
        tempLinkedHashMap.put(this.yamlNodeName, stepsList);
        this.parentNode = tempLinkedHashMap;
    }

    @Override
    public Optional<StepsModel> parse() {
        try {
            Object stepsNode = this.getValue(parentNode, this.yamlNodeName);
            if (stepsNode instanceof LinkedHashMap) {
                return Optional.of(new StepsModel(new ScriptParser((LinkedHashMap) stepsNode).parse()));
            } else if (stepsNode instanceof String) {
                return Optional.of(new StepsModel((String) stepsNode));
            } else {
                return Optional.of(new StepsModel((List) stepsNode));
            }
        } catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
