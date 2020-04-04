package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class OptionsModel extends AbstractModel implements ParsableModelInterface {

    private List<String> optionList;

    public OptionsModel(List<String> optionList) {
        this.optionList = optionList;
    }
}
