package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.StageModel;
import org.jenkinsci.plugins.pipeline.yaml.models.StagesModel;
import org.jenkinsci.plugins.pipeline.yaml.models.StepsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StagesParser extends AbstractParser implements ParserInterface<StagesModel> {

    private LinkedHashMap stagesNode;
    private LinkedHashMap pipelineNode;

    public StagesParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "stages";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public StagesModel parse() {
        List<StageModel> stageModelList = new ArrayList<>();
        Object stagesObject = this.pipelineNode.get(this.yamlNodeName);
        if( stagesObject instanceof List){
            for(LinkedHashMap childStage : (List<LinkedHashMap>)stagesObject){
                StageModel stageModel = new StageParser(childStage).parse();
                stageModelList.add(stageModel);
            }
        }
        return new StagesModel(stageModelList);
    }
}
