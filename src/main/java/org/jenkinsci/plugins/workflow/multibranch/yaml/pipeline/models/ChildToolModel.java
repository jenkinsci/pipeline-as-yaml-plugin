package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

/**
 * Model Class for Jenkins Declarative Pipeline Tool Section
 */
@Getter
public class ChildToolModel extends AbstractModel implements ParsableModelInterface {

    private String toolType;
    private String toolName;

    /**
     * @param toolType Tool type
     * @param toolName Name of the tool which is defined in Jenkins
     */
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
