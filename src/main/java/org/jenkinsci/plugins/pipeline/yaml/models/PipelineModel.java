package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.*;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Optional;

@Getter
@Setter
@Builder
public class PipelineModel extends AbstractModel implements ParsableModelInterface {

    public static String directive = "pipeline";
    private Optional<AgentModel> agent;
    private Optional<PostModel> post;
    private Optional<EnvironmentModel> environment;
    private Optional<ToolsModel> tools;
    private Optional<OptionsModel> options;
    private Optional<ParametersModel> parameters;
    private Optional<TriggersModel> triggers;
    private Optional<StagesModel> stages;

    @Override
    public String toGroovy() {
        return new StringBuffer()
                .append(directive)
                .append(getDirectiveOpen())
                .append(agent.map(AgentModel::toGroovy).orElse(""))
                .append(tools.map(ToolsModel::toGroovy).orElse(""))
                .append(environment.map(EnvironmentModel::toGroovy).orElse(""))
                .append(options.map(OptionsModel::toGroovy).orElse(""))
                .append(parameters.map(ParametersModel::toGroovy).orElse(""))
                .append(triggers.map(TriggersModel::toGroovy).orElse(""))
                .append(stages.map(StagesModel::toGroovy).orElse(""))
                .append(post.map(PostModel::toGroovy).orElse(""))
                .append(getDirectiveClose()).toString();
    }
}
