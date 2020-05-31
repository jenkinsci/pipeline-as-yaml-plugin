package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStages;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StageModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StagesModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class StagesParser extends AbstractParser implements ParserInterface<StagesModel, ModelASTStages> {

    public StagesParser(){
        this.yamlNodeName = StagesModel.directive;
    }

    @Override
    public Optional<StagesModel> parse(LinkedHashMap parentNode) {
        List<StageModel> stageModelList = new ArrayList<>();
        Object stagesObject = parentNode.get(this.yamlNodeName);
        if( stagesObject == null ){
            return Optional.empty();
        }
        if (stagesObject instanceof List) {
            for (LinkedHashMap childStage : (List<LinkedHashMap>) stagesObject) {
                Optional<StageModel> stageModel = new StageParser().parse(childStage);
                stageModel.ifPresent(stageModelList::add);
            }
        }
        return Optional.of(new StagesModel(stageModelList));
    }

    @Override
    public Optional<StagesModel> parse(ModelASTStages modelAST) {
        return Optional.empty();
    }
}
