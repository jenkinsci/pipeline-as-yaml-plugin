package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;


@Getter
@Setter
public class ChildToolModel extends AbstractModel implements ParsableModelInterface {

    private String toolType;
    private String toolName;

    public ChildToolModel(String toolType, String toolName) {
        this.toolType = toolType;
        this.toolName = toolName;
    }

    @Override
    public String toGroovy() {
        return new StringBuffer()
                .append(toolType)
                .append(getParameterOpen())
                .append(toolName)
                .append(getParameterClose())
                .toString();
    }
}
