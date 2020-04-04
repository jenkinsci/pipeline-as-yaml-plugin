package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.*;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;

@Getter
@Setter
@Builder
public class PipelineModel extends AbstractModel implements ParsableModelInterface {

    private AgentModel agent;
    private PostModel post;
    private EnvironmentModel environment;
    private ToolsModel tools;
    private OptionsModel options;

}
