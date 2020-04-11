package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.StageModel;
import org.jenkinsci.plugins.pipeline.yaml.models.StagesModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class StagesParser extends AbstractParser implements ParserInterface<StagesModel> {

    private LinkedHashMap parentNode;

    public StagesParser(LinkedHashMap parentNode){
        this.yamlNodeName = "stages";
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StagesModel> parse() {
        List<StageModel> stageModelList = new ArrayList<>();
        Object stagesObject = this.parentNode.get(this.yamlNodeName);
        if( stagesObject == null ){
            return Optional.empty();
        }
        if (stagesObject instanceof List) {
            for (LinkedHashMap childStage : (List<LinkedHashMap>) stagesObject) {
                Optional<StageModel> stageModel = new StageParser(childStage).parse();
                stageModel.ifPresent(stageModelList::add);
            }
        }
        return Optional.of(new StagesModel(stageModelList));
    }
}
