package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStage;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.*;

import java.util.LinkedHashMap;
import java.util.Optional;

public class StageParser extends AbstractParser implements ParserInterface<StageModel, ModelASTStage> {

    private String failFastKey= StageModel.failFastKey;
    private String beforeAgentKey = StageModel.beforeAgentKey;

    public StageParser() {
        this.yamlNodeName = "stage";
    }

    @Override
    public Optional<StageModel> parse(LinkedHashMap parentNode) {
        String name = (String) parentNode.get(this.yamlNodeName);
        //FIXME Should be parsed via order
        Optional<Boolean> failFast = Optional.ofNullable((Boolean) parentNode.get(this.failFastKey));
        Optional<StepsModel> stepsModel = new StepsParser().parse(parentNode);
        Optional<AgentModel> agentModel = new AgentParser().parse(parentNode);
        Optional<PostModel> postModel = new PostParser().parse(parentNode);
        Optional<ToolsModel> toolsModel = new ToolsParser().parse(parentNode);
        Optional<StagesModel> stagesModel = new StagesParser().parse(parentNode);
        Optional<EnvironmentModel> environmentModel = new EnvironmentParser().parse(parentNode);
        Optional<ParallelModel> parallelModel = new ParallelParser().parse(parentNode);
        Optional<InputModel> inputModel = new InputParser().parse(parentNode);
        Optional<Boolean> beforeAgent = Optional.ofNullable((Boolean) parentNode.get(this.beforeAgentKey));
        Optional<WhenModel> whenModel = new WhenParser().parse(parentNode);
        Optional<OptionsModel> optionsModel = new OptionsParser().parse(parentNode);

        return Optional.of(new StageModel(name, stepsModel, agentModel, postModel, toolsModel, stagesModel, environmentModel,parallelModel,failFast, inputModel,whenModel, beforeAgent, optionsModel));
    }

    @Override
    public Optional<StageModel> parse(ModelASTStage modelAST) {
        return Optional.empty();
    }
}
