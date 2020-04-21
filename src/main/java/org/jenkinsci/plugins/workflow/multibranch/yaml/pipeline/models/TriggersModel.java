package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class TriggersModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "triggers";
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
        triggersList.stream().forEach(trigger -> {
            groovyString.append(trigger).append("\n");
        });
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
