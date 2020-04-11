package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class StepsModel extends AbstractModel implements ParsableModelInterface {

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
}
