package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StageParser extends AbstractParser implements ParserInterface<StageModel> {

    private LinkedHashMap stageNode;
    private LinkedHashMap pipelineNode;
    private String stageNameKey = "name";

    public StageParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "stage";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public StageModel parse() {
        String name = (String) this.pipelineNode.get(this.stageNameKey);
        StepsModel stepsModel = new StepsParser(this.pipelineNode).parse();
        AgentModel agentModel = new AgentParser(this.pipelineNode, false).parse();
        PostModel postModel = new PostParser(this.pipelineNode).parse();
        ToolsModel toolsModel = new ToolsParser(this.pipelineNode).parse();
        return new StageModel(name,stepsModel,agentModel,postModel,toolsModel);
    }
}
