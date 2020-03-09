package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class PostModel extends AbstractModel implements ParsableModelInterface {

    private String postType;
    private List<String> postSteps;
    private ScriptModel script;

    public PostModel(String postType, List<String> postSteps, ScriptModel script) {
        this.postType = postType;
        this.postSteps = postSteps;
        this.script = script;
    }

}
