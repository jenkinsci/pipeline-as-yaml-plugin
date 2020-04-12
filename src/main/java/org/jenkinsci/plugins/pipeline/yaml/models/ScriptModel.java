package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class ScriptModel extends AbstractModel implements ParsableModelInterface {

    private List<String> scripts;

    public ScriptModel(List<String> scripts) {
        this.scripts = scripts;
    }

    public ScriptModel(String scripts) {
        this.scripts = Arrays.asList(scripts.split("\n"));
    }
}
