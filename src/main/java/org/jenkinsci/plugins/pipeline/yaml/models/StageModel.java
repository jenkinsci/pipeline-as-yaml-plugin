package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Optional;


@Getter
@Setter
public class StageModel extends AbstractModel implements ParsableModelInterface {

    private String name;
    private Optional<StepsModel> stepsModel;
    private Optional<AgentModel> agentModel;
    private Optional<PostModel> postModel;
    private Optional<ToolsModel> toolsModel;
    private Optional<StagesModel> stagesModel;
    private Optional<EnvironmentModel> environmentModel;
    private Optional<ParallelModel> parallelModel;
    private Optional<Boolean> failFast = Optional.of(false);


    public StageModel(String name, Optional<StepsModel> stepsModel, Optional<AgentModel> agentModel, Optional<PostModel> postModel, Optional<ToolsModel> toolsModel, Optional<StagesModel> stagesModel, Optional<EnvironmentModel> environmentModel, Optional<ParallelModel> parallelModel, Optional<Boolean> failFast) {
        this.name = name;
        this.stepsModel = stepsModel;
        this.agentModel = agentModel;
        this.postModel = postModel;
        this.toolsModel = toolsModel;
        this.stagesModel = stagesModel;
        this.environmentModel = environmentModel;
        this.parallelModel = parallelModel;
        this.failFast = failFast;
    }
}
