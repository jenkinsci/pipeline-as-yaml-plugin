package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StepsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class StepsParser extends AbstractParser implements ParserInterface<StepsModel> {

    private LinkedHashMap parentNode;

    public StepsParser(LinkedHashMap parentNode) {
        this.yamlNodeName = StepsModel.directive;
        this.parentNode = parentNode;
    }

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
