package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

/**
 * Model Class for Jenkins Declarative Pipeline Child Tool Section
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
