package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTTriggers;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.TriggersModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class TriggersParser extends AbstractParser implements ParserInterface<TriggersModel, ModelASTTriggers> {

    private List triggersNode;

    public TriggersParser(){
        this.yamlNodeName = TriggersModel.directive;
    }

    @Override
    public Optional<TriggersModel> parse(LinkedHashMap parentNode) {
        try {
            this.triggersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new TriggersModel(this.triggersNode));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TriggersModel> parse(ModelASTTriggers modelAST) {
        return Optional.empty();
    }
}
