package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.*;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Optional;

@Getter
@Setter
@Builder
public class PipelineModel extends AbstractModel implements ParsableModelInterface {

    private Optional<AgentModel> agent;
    private Optional<PostModel> post;
    private Optional<EnvironmentModel> environment;
    private Optional<ToolsModel> tools;
    private Optional<OptionsModel> options;
    private Optional<ParametersModel> parameters;
    private Optional<TriggersModel> triggers;
    private Optional<StagesModel> stages;

}
