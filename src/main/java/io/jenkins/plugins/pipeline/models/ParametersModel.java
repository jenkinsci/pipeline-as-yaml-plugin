package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Parameters Section
 */
@Getter
public class ParametersModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "parameters";
    private List<String> parametersList;

    /**
     * @param parametersList List pf parameters
     */
    public ParametersModel(List<String> parametersList) {
        this.parametersList = parametersList;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(directive)
                .append(this.getDirectiveOpen());
        parametersList.stream().forEach(parameter -> {
            groovyString.append(parameter).append("\n");
        });
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
