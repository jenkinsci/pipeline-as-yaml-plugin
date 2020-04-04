package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class TriggersModel extends AbstractModel implements ParsableModelInterface {

    private List<String> triggersList;

    public TriggersModel(List<String> triggersList) {
        this.triggersList = triggersList;
    }
}
