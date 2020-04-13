package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class OptionsModel extends AbstractModel implements ParsableModelInterface {

    public static String directive = "options";
    private List<String> optionList;

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
