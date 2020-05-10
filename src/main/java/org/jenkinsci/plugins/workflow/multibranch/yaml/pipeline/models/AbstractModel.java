package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

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
    private String getBracketsOpen = "(";
    private String getBracketsClose = ")";

    protected String optionalStringToGroovy(Optional<String> option, String optionKey) {
        return option.map(s -> new StringBuffer()
                .append(optionKey)
                .append(getStringOpen())
                .append(s)
                .append(getStringClose())
                .toString()).orElse("");
    }

    protected String optionalBooleanToGroovy(Optional<Boolean> option, String optionKey) {
        return option.map(aBoolean -> new StringBuffer()
                .append(optionKey)
                .append(" ")
                .append(aBoolean)
                .toString()).orElse("");
    }

}
