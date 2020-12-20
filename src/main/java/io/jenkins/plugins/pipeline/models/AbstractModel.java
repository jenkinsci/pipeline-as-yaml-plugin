package io.jenkins.plugins.pipeline.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Abstract Model class which is extended by Model classes
 */
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
    private String libraryOpen = "@Library(";
    private String libraryClose = ") _\n";
    private String environmentVariableOpen = " = ";
    private String environmentVariableClose = "\n";
    private String singleQuote = "'";
    private String doubleQuote = "\"";
    private String variableDoubleQuoteOpen = " = '";
    private String variableDoubleQuoteClose = "'\n";
    private String credentialsFunctionName = "credentials";

    /**
     * Convert {@link Optional} object to Groovy Script with given Option Key
     * @param option Option
     * @param optionKey Option Key
     * @return Groovy Script
     */
    protected String optionalStringToGroovy(Optional<String> option, String optionKey) {
        return option.map(s -> new StringBuffer()
                .append(optionKey)
                .append(getStringOpen())
                .append(s)
                .append(getStringClose())
                .toString()).orElse("");
    }

    /**
     * Convert {@link Optional} object to Groovy Syntax with given Option Key
     * @param option Option
     * @param optionKey Option Key
     * @return Groovy Script
     */
    protected String optionalBooleanToGroovy(Optional<Boolean> option, String optionKey) {
        return option.map(aBoolean -> new StringBuffer()
                .append(optionKey)
                .append(" ")
                .append(aBoolean)
                .toString()).orElse("");
    }

}
