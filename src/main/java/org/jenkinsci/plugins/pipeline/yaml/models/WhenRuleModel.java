package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;


@Getter
@Setter
public class WhenRuleModel extends AbstractModel implements ParsableModelInterface {

    private String whenRule;

    public WhenRuleModel(String whenRule) {
        this.whenRule = whenRule;
    }
}
