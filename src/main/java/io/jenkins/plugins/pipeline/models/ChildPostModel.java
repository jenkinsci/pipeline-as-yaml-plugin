package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline Post Section Conditions
 */
@Getter
public class ChildPostModel extends AbstractModel implements ParsableModelInterface {

    private String postType;
    private Optional<StepsModel> postSteps;
    private Optional<ScriptModel> postScript;

    /**
     * @param postType   Post condition type
     * @param postSteps  Post {@link StepsModel}
     * @param postScript post {@link ScriptModel}
     */
    public ChildPostModel(String postType, Optional<StepsModel> postSteps, Optional<ScriptModel> postScript) {
        this.postType = postType;
        this.postSteps = postSteps;
        this.postScript = postScript;
    }


    @Override
    public String toGroovy() {
        return new StringBuffer()
                .append(postType)
                .append(getDirectiveOpen())
                .append(postSteps.map(StepsModel::toGroovyForPostModel).orElse(""))
                .append(postScript.map(ScriptModel::toGroovy).orElse(""))
                .append(getDirectiveClose())
                .toString();
    }
}
