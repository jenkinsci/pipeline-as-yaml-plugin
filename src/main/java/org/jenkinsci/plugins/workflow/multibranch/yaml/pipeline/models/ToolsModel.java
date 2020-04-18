package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class ToolsModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "tools";
    private List<ChildToolModel> childToolModels;

    public ToolsModel(List<ChildToolModel> childToolModels) {
        this.childToolModels = childToolModels;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString  = new StringBuffer()
                .append(directive)
                .append(getDirectiveOpen());
        childToolModels.forEach(childToolModel -> groovyString.append(childToolModel.toGroovy()));
        return groovyString.append(getDirectiveClose()).toString();
    }
}
