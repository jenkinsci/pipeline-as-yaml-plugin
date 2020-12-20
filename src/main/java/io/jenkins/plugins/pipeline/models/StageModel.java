package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline Stage Section
 */
@Getter
@Setter
public class StageModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "stage";
    public static final String failFastKey = "failFast";
    public static final String beforeAgentKey = "beforeAgent";
    private String name;
    private Optional<StepsModel> stepsModel;
    private Optional<AgentModel> agentModel;
    private Optional<PostModel> postModel;
    private Optional<ToolsModel> toolsModel;
    private Optional<StagesModel> stagesModel;
    private Optional<EnvironmentModel> environmentModel;
    private Optional<ParallelModel> parallelModel;
    private Optional<Boolean> failFast;
    private Optional<InputModel> inputModel;
    private Optional<WhenModel> whenModel;
    private Optional<OptionsModel> optionsModel;


    /**
     * @param name Name of the stage
     * @param stepsModel {@link StepsModel}
     * @param agentModel {@link AgentModel}
     * @param postModel {@link PostModel}
     * @param toolsModel {@link ToolsModel}
     * @param stagesModel {@link StagesModel}
     * @param environmentModel {@link EnvironmentModel}
     * @param parallelModel {@link ParallelModel}
     * @param failFast Flag for failFast Option
     * @param inputModel {@link InputModel}
     * @param whenModel {@link WhenModel}
     * @param beforeAgent Flag for beforeAgent option
     * @param optionsModel {@link OptionsModel}
     */
    public StageModel(String name, Optional<StepsModel> stepsModel, Optional<AgentModel> agentModel, Optional<PostModel> postModel, Optional<ToolsModel> toolsModel, Optional<StagesModel> stagesModel, Optional<EnvironmentModel> environmentModel, Optional<ParallelModel> parallelModel, Optional<Boolean> failFast, Optional<InputModel> inputModel, Optional<WhenModel> whenModel, Optional<Boolean> beforeAgent, Optional<OptionsModel> optionsModel) {
        this.name = name;
        this.stepsModel = stepsModel;
        this.agentModel = agentModel;
        this.postModel = postModel;
        this.toolsModel = toolsModel;
        this.stagesModel = stagesModel;
        this.environmentModel = environmentModel;
        this.parallelModel = parallelModel;
        this.failFast = failFast;
        this.inputModel = inputModel;
        this.optionsModel = optionsModel;
        this.whenModel = whenModel;
        this.whenModel.ifPresent(model -> model.setBeforeAgent(beforeAgent));
    }

    @Override
    public String toGroovy() {
        //FIXME There should be order
        StringBuffer groovyString = new StringBuffer();
        groovyString.append(getStageOpen())
                .append(this.name)
                .append(getStageClose())
                .append(getDirectiveOpen())
                .append(agentModel.map(AgentModel::toGroovy).orElse(""))
                .append(environmentModel.map(EnvironmentModel::toGroovy).orElse(""))
                .append(toolsModel.map(ToolsModel::toGroovy).orElse(""))
                .append(inputModel.map(InputModel::toGroovy).orElse(""))
                .append(whenModel.map(WhenModel::toGroovy).orElse(""))
                .append(stagesModel.map(StagesModel::toGroovy).orElse(""))
                .append(optionalBooleanToGroovy(failFast, failFastKey)).append("\n")
                .append(parallelModel.map(ParallelModel::toGroovy).orElse(""))
                .append(stepsModel.map(StepsModel::toGroovy).orElse(""))
                .append(postModel.map(PostModel::toGroovy).orElse(""))
                .append(getDirectiveClose());
        return groovyString.toString();
    }
}
