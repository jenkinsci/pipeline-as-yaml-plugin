package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;


@Getter
@Setter
public class StageModel extends AbstractModel implements ParsableModelInterface {

    private String name;
    private StepsModel stepsModel;
    private AgentModel agentModel;
    private PostModel postModel;
    private ToolsModel toolsModel;

    public StageModel(String name, StepsModel stepsModel, AgentModel agentModel, PostModel postModel, ToolsModel toolsModel) {
        this.name = name;
        this.stepsModel = stepsModel;
        this.agentModel = agentModel;
        this.postModel = postModel;
        this.toolsModel = toolsModel;
    }
}
