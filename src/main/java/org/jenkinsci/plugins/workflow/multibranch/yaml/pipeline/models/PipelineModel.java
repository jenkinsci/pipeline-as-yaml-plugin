package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.ivy.util.StringUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.Optional;

@Getter
@Setter
@Builder
public class PipelineModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "pipeline";
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

    public String toPrettyGroovy() {
        StringBuffer prettyGroovyString = new StringBuffer();
        String groovyString = this.toGroovy();
        String[] parsedString = groovyString.split("\n");
        int indentCounter = 0;
        for (String line : parsedString) {
            if (line.length() == 0)
                continue;
            if (line.endsWith("{")) {
                line = indent(indentCounter) + line + "\n";
                indentCounter++;
            } else if (line.startsWith("}")) {
                indentCounter--;
                line = indent(indentCounter) + line + "\n";
            } else {
                line = indent(indentCounter) + line + "\n";
            }
            prettyGroovyString.append(line);
        }
        return prettyGroovyString.toString();
    }

    private String indent(int count) {
        return StringUtils.repeat("  ", (Math.max(count, 0)));
    }

}
