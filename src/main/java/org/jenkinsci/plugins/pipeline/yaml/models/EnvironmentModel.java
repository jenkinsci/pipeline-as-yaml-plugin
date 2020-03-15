package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class EnvironmentModel extends AbstractModel implements ParsableModelInterface {

    private List<KeyValueModel> environmentVariables;

    public EnvironmentModel(List<KeyValueModel> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }
}
