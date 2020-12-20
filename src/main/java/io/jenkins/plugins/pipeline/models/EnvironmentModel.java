package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Environment Section
 */
@Getter
public class EnvironmentModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "environment";
    private List<EnvironmentVariableModel> environmentVariables;

    /**
     * @param environmentVariables List of {@link VariableModel}
     */
    public EnvironmentModel(List<EnvironmentVariableModel> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }


    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive)
                .append(this.getDirectiveOpen());
        environmentVariables.forEach(variableModel -> groovyString.append(variableModel.toGroovy()));
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
