package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Model Class for Jenkins Declarative Pipeline Tool Section
 */
@Getter
@Setter
public class ToolsModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "tools";
    private List<ChildToolModel> childToolModels;

    /**
     * @param childToolModels List of {@link ChildToolModel}
     */
    public ToolsModel(List<ChildToolModel> childToolModels) {
        this.childToolModels = childToolModels;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer().append(directive).append(getDirectiveOpen());
        childToolModels.forEach(childToolModel -> groovyString.append(childToolModel.toGroovy()));
        return groovyString.append(getDirectiveClose()).toString();
    }
}
