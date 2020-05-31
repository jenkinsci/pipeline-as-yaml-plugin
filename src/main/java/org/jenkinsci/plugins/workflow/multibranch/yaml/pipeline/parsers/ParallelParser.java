package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStages;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ParallelModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StageModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ParallelParser extends AbstractParser implements ParserInterface<ParallelModel> {

    public ParallelParser(){
        this.yamlNodeName = ParallelModel.directive;
    }

    @Override
    public Optional<ParallelModel> parse(LinkedHashMap parentNode) {
        List<StageModel> stageModelList = new ArrayList<>();
        Object parallelObject = parentNode.get(this.yamlNodeName);
        if( parallelObject == null ){
            return Optional.empty();
        }
        if (parallelObject instanceof List) {
            for (LinkedHashMap childStage : (List<LinkedHashMap>) parallelObject) {
                Optional<StageModel> stageModel = new StageParser().parse(childStage);
                stageModel.ifPresent(stageModelList::add);
            }
        }
        return Optional.of(new ParallelModel(stageModelList));
    }

    @Override
    public Optional<ParallelModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
