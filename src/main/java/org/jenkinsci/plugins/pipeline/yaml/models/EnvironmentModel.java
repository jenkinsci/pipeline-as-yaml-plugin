package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class EnvironmentModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "environment";
    private List<VariableModel> environmentVariables;

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
