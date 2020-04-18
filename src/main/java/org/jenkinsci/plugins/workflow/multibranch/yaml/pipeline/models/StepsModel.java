package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class StepsModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "steps";
    private List<String> steps = new ArrayList<>();
    private Optional<ScriptModel> script = Optional.empty();

    public StepsModel(List<String> steps) {
        this.steps = steps;
    }

    public StepsModel(Optional<ScriptModel> script) {
        this.script = script;
    }

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

    public String toGroovyForPostModel() {
        StringBuffer groovyString = new StringBuffer();
        steps.stream().forEach(step -> {
            groovyString.append(step).append("\n");
        });
        return groovyString.toString();
    }


}
