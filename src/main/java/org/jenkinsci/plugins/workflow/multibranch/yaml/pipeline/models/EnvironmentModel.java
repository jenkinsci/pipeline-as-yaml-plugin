package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Environment Section
 */
@Getter
public class EnvironmentModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "environment";
    private List<VariableModel> environmentVariables;

    /**
     * @param environmentVariables List of {@link VariableModel}
     */
    public EnvironmentModel(List<VariableModel> environmentVariables) {
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
