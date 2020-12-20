package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline Steps Section
 */
@Getter
@Setter
public class StepsModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "steps";
    private List<String> steps = new ArrayList<>();
    private Optional<ScriptModel> script = Optional.empty();

    /**
     * @param steps List of Steps
     */
    public StepsModel(List<String> steps) {
        this.steps = steps;
    }

    /**
     * @param script {@link ScriptModel}
     */
    public StepsModel(Optional<ScriptModel> script) {
        this.script = script;
    }

    /**
     * @param steps Step
     */
    public StepsModel(String steps) {
        this.steps = Arrays.asList(steps.split("\n"));
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(directive)
                .append(this.getDirectiveOpen());
        steps.stream().forEach(step -> {
            groovyString.append(step).append("\n");
        });
        groovyString
                .append(script.map(ScriptModel::toGroovy).orElse(""))
                .append(this.getDirectiveClose());
        return groovyString.toString();
    }

    /**
     * Convert steps to Groovy Format for Post Section
     * @return Steps in Groovy Format for Post Section
     */
    public String toGroovyForPostModel() {
        StringBuffer groovyString = new StringBuffer();
        steps.stream().forEach(step -> {
            groovyString.append(step).append("\n");
        });
        return groovyString.toString();
    }


}
