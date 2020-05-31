package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTWhenCondition;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.WhenConditionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class WhenConditionalParser extends AbstractParser implements ParserInterface<WhenConditionModel, ModelASTWhenCondition> {

    public WhenConditionalParser() {
    }

    @Override
    public Optional<WhenConditionModel> parse(LinkedHashMap parentNode) {
        try {
            String conditionKey = this.getKey(parentNode);
            Object conditionObject = this.getValue(parentNode, conditionKey);
            if( conditionObject instanceof  List) {
                return Optional.of(new WhenConditionModel(conditionKey, (List<String>) conditionObject));
            }
            else if (conditionObject instanceof  LinkedHashMap) {
                return Optional.of(new WhenConditionModel(conditionKey,new WhenConditionalParser().parse((LinkedHashMap) conditionObject)));
            }
            return Optional.empty();
        }
        catch (PipelineAsYamlKeyEmptyException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<WhenConditionModel> parse(ModelASTWhenCondition modelAST) {
        return Optional.empty();
    }
}
