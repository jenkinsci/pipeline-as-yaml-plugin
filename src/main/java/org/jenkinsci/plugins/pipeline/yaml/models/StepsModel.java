package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class StepsModel extends AbstractModel implements ParsableModelInterface {

    private List<String> steps;

    public StepsModel(List<String> steps) {
        this.steps = steps;
    }
}
