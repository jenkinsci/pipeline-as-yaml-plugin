package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Triggers Section
 */
@Getter
@Setter
public class TriggersModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "triggers";
    private List<String> triggersList;

    /**
     * @param triggersList List of trigger definitions
     */
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
