package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.*;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Parser for {@link StageModel}
 */
public class StageParser extends AbstractParser implements ParserInterface<StageModel> {

    private LinkedHashMap parentNode;
    private String failFastKey= StageModel.failFastKey;
    private String beforeAgentKey = StageModel.beforeAgentKey;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public StageParser(LinkedHashMap parentNode) {
        this.yamlNodeName = "stage";
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StageModel> parse() {
        try {
            String name = this.getChildNodeAsString(this.parentNode);
            Optional<Boolean> failFast = Optional.ofNullable((Boolean) this.parentNode.get(this.failFastKey));
            Optional<StepsModel> stepsModel = new StepsParser(this.parentNode).parse();
            Optional<AgentModel> agentModel = new AgentParser(this.parentNode).parse();
            Optional<PostModel> postModel = new PostParser(this.parentNode).parse();
            Optional<ToolsModel> toolsModel = new ToolsParser(this.parentNode).parse();
            Optional<StagesModel> stagesModel = new StagesParser(this.parentNode).parse();
            Optional<EnvironmentModel> environmentModel = new EnvironmentParser(this.parentNode).parse();
            Optional<ParallelModel> parallelModel = new ParallelParser(this.parentNode).parse();
            Optional<InputModel> inputModel = new InputParser(this.parentNode).parse();
            Optional<Boolean> beforeAgent = Optional.ofNullable((Boolean) this.parentNode.get(this.beforeAgentKey));
            Optional<WhenModel> whenModel = new WhenParser(this.parentNode).parse();
            Optional<OptionsModel> optionsModel = new OptionsParser(this.parentNode).parse();
            return Optional.of(new StageModel(name, stepsModel, agentModel, postModel, toolsModel, stagesModel, environmentModel, parallelModel, failFast, inputModel, whenModel, beforeAgent, optionsModel));
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
