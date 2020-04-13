package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class StageParser extends AbstractParser implements ParserInterface<StageModel> {

    private LinkedHashMap parentNode;
    private String failFastKey= StageModel.failFastKey;
    private String beforeAgentKey = StageModel.beforeAgentKey;
    private String beforeOptionsKey = StageModel.beforeOptionsKey;
    private String beforeInputKey = StageModel.beforeInputKey;

    public StageParser(LinkedHashMap parentNode) {
        this.yamlNodeName = "stage";
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StageModel> parse() {
        String name = (String) this.parentNode.get(this.yamlNodeName);
        //FIXME Should be parsed via order
        Optional<Boolean> failFast = Optional.ofNullable((Boolean) this.parentNode.get(this.failFastKey));
        Optional<StepsModel> stepsModel = new StepsParser(this.parentNode).parse();
        Optional<AgentModel> agentModel = new AgentParser(this.parentNode).parse();
        Optional<PostModel> postModel = new PostParser(this.parentNode).parse();
        Optional<ToolsModel> toolsModel = new ToolsParser(this.parentNode).parse();
        Optional<StagesModel> stagesModel = new StagesParser(this.parentNode).parse();
        Optional<EnvironmentModel> environmentModel = new EnvironmentParser(this.parentNode).parse();
        Optional<ParallelModel> parallelModel = new ParallelParser(this.parentNode).parse();
        Optional<InputModel> inputModel = new InputParser(this.parentNode).parse();
        Optional<WhenModel> whenModel = new WhenParser(this.parentNode).parse();
        Optional<Boolean> beforeAgent = Optional.ofNullable((Boolean) this.parentNode.get(this.beforeAgentKey));
        Optional<Boolean> beforeOptions = Optional.ofNullable((Boolean) this.parentNode.get(this.beforeOptionsKey));
        Optional<Boolean> beforeInput = Optional.ofNullable((Boolean) this.parentNode.get(this.beforeInputKey));
        return Optional.of(new StageModel(name, stepsModel, agentModel, postModel, toolsModel, stagesModel, environmentModel,parallelModel,failFast, inputModel,whenModel, beforeAgent,beforeOptions,beforeInput));
    }
}
