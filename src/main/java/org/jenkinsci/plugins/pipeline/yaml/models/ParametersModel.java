package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ParametersModel extends AbstractModel implements ParsableModelInterface {

    private List<String> parametersList;

    public ParametersModel(List<String> parametersList) {
        this.parametersList = parametersList;
    }
}
