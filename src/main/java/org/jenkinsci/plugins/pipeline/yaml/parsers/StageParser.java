package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class StageParser extends AbstractParser implements ParserInterface<StageModel> {

    private LinkedHashMap parentNode;
    private String stageNameKey = "name";

    public StageParser(LinkedHashMap parentNode) {
        this.yamlNodeName = "stage";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StageModel> parse() {
        String name = (String) this.parentNode.get(this.stageNameKey);
        Optional<StepsModel> stepsModel = new StepsParser(this.parentNode).parse();
        Optional<AgentModel> agentModel = new AgentParser(this.parentNode, false).parse();
        Optional<PostModel> postModel = new PostParser(this.parentNode).parse();
        Optional<ToolsModel> toolsModel = new ToolsParser(this.parentNode).parse();
        return Optional.of(new StageModel(name, stepsModel, agentModel, postModel, toolsModel));
    }
}
