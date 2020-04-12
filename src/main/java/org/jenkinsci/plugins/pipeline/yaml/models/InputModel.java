package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class InputModel extends AbstractModel implements ParsableModelInterface {

    private String message;
    private Optional<String> id;
    private Optional<String> ok;
    private Optional<String> submitter;
    private Optional<String> submitterParameter;
    private Optional<ParametersModel> parametersModel;

    public InputModel(String message, Optional<String> id, Optional<String> ok, Optional<String> submitter, Optional<String> submitterParameter, Optional<ParametersModel> parametersModel) {
        this.message = message;
        this.id = id;
        this.ok = ok;
        this.submitter = submitter;
        this.submitterParameter = submitterParameter;
        this.parametersModel = parametersModel;
    }
}
