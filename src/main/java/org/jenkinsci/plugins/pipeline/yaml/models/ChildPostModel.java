package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Optional;


@Getter
@Setter
public class ChildPostModel extends AbstractModel implements ParsableModelInterface {

    private String postType;
    private Optional<StepsModel> postSteps;
    private Optional<ScriptModel> postScript;

    public ChildPostModel(String postType, Optional<StepsModel> postSteps, Optional<ScriptModel> postScript) {
        this.postType = postType;
        this.postSteps = postSteps;
        this.postScript = postScript;
    }

}
