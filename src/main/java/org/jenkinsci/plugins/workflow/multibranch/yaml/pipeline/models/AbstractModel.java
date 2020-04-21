package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public abstract class AbstractModel {

    private String directiveOpen = " {\n";
    private String directiveClose = "\n}\n";
    private String parameterOpen = " '";
    private String parameterClose = "'\n";
    private String variableOpen = " = '";
    private String variableClose = "'\n";
    private String stringOpen = " \"";
    private String stringClose = "\"\n";
    private String stageOpen = "stage('";
    private String stageClose = "')";

    protected String optionalStringToGroovy(Optional<String> option, String optionKey) {
        if(!option.isPresent())
            return "";
        return new StringBuffer()
                .append(optionKey)
                .append(getStringOpen())
                .append(option.get())
                .append(getStringClose())
                .toString();
    }

    protected String optionalBooleanToGroovy(Optional<Boolean> option, String optionKey) {
        if(!option.isPresent())
            return "";
        return new StringBuffer()
                .append(optionKey)
                .append(" ")
                .append(option.get())
                .toString();
    }

}
