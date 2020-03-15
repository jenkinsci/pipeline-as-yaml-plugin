package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class PostModel extends AbstractModel implements ParsableModelInterface {

    private String postType;
    private StepsModel postSteps;
    private ScriptModel postScript;

    public PostModel(String postType, StepsModel postSteps, ScriptModel postScript) {
        this.postType = postType;
        this.postSteps = postSteps;
        this.postScript = postScript;
    }

}
