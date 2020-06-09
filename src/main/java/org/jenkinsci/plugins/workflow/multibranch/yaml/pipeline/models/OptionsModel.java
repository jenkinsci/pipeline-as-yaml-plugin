package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Options Section
 */
@Getter
public class OptionsModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "options";
    private List<String> optionList;

    /**
     * @param optionList List of options
     */
    public OptionsModel(List<String> optionList) {
        this.optionList = optionList;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(directive)
                .append(this.getDirectiveOpen());
        optionList.stream().forEach(option ->{
            groovyString.append(option).append("\n");
        });
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
