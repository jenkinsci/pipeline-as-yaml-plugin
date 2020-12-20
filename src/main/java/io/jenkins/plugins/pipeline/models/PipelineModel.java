package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Builder;
import lombok.Getter;
import org.apache.ivy.util.StringUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;

import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline
 */
@Getter
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
    private Optional<LibraryModel> library;

    @Override
    public String toGroovy() {
        return new StringBuffer()
                .append(library.map(LibraryModel::toGroovy).orElse(""))
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

    /**
     * Converts Model to Pretty Jenkins Declarative Pipeline Syntax
     * @return
     */
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

    /**
     * Create indent at given amount
     * @param count Number of indents
     * @return Indent string
     */
    private String indent(int count) {
        return StringUtils.repeat("  ", (Math.max(count, 0)));
    }

    /**
     * Convert Model to {@link ModelASTPipelineDef}
     * @return {@link ModelASTPipelineDef}
     */
    public ModelASTPipelineDef validate() {
        String pipelineString = this.toPrettyGroovy();
        return Converter.scriptToPipelineDef(pipelineString);
    }

}
