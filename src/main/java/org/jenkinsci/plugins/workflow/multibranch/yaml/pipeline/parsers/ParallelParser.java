package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ParallelModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.StageModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ParallelParser extends AbstractParser implements ParserInterface<ParallelModel> {

    private LinkedHashMap parentNode;

    public ParallelParser(LinkedHashMap parentNode){
        this.yamlNodeName = ParallelModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ParallelModel> parse() {
        List<StageModel> stageModelList = new ArrayList<>();
        Object parallelObject = this.parentNode.get(this.yamlNodeName);
        if( parallelObject == null ){
            return Optional.empty();
        }
        if (parallelObject instanceof List) {
            for (LinkedHashMap childStage : (List<LinkedHashMap>) parallelObject) {
                Optional<StageModel> stageModel = new StageParser(childStage).parse();
                stageModel.ifPresent(stageModelList::add);
            }
        }
        return Optional.of(new ParallelModel(stageModelList));
    }
}
