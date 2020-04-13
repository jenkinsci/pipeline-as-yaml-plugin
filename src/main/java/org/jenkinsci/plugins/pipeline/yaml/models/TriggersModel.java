package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class TriggersModel extends AbstractModel implements ParsableModelInterface {

    public static String directive = "triggers";
    private List<String> triggersList;

    public TriggersModel(List<String> triggersList) {
        this.triggersList = triggersList;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(directive)
                .append(this.getDirectiveOpen());
        triggersList.stream().forEach(groovyString::append);
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
