package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Post Section
 */
@Getter
public class PostModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "post";
    private List<ChildPostModel> childPostModels;

    /**
     * @param childPostModels List of {@link ChildPostModel}
     */
    public PostModel(List<ChildPostModel> childPostModels) {
        this.childPostModels = childPostModels;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive)
                .append(getDirectiveOpen());
        childPostModels.forEach(childPostModel -> groovyString.append(childPostModel.toGroovy()));
        return groovyString.append(getDirectiveClose()).toString();
    }
}
