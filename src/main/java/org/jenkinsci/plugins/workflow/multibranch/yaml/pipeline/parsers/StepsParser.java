package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStep;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StepsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class StepsParser extends AbstractParser implements ParserInterface<StepsModel> {

    private LinkedHashMap parentNode;

    public StepsParser(){
        this.yamlNodeName = StepsModel.directive;
    }

    public Optional<StepsModel> parse(List stepsList){
        LinkedHashMap tempLinkedHashMap = new LinkedHashMap();
        tempLinkedHashMap.put(this.yamlNodeName, stepsList);
        return this.parse(tempLinkedHashMap);
    }

    @Override
    public Optional<StepsModel> parse(LinkedHashMap parentNode) {
        try {
            Object stepsNode = this.getValue(parentNode, this.yamlNodeName);
            if (stepsNode instanceof LinkedHashMap) {
                return Optional.of(new StepsModel(new ScriptParser().parse((LinkedHashMap) stepsNode)));
            } else if (stepsNode instanceof String) {
                return Optional.of(new StepsModel((String) stepsNode));
            } else {
                return Optional.of(new StepsModel((List) stepsNode));
            }
        }
        catch (PipelineAsYamlException p)
        {
            return Optional.empty();
        }
    }

    @Override
    public Optional<StepsModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
