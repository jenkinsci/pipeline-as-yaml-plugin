package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class InputModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "input";
    public static final String messageKey = "message";
    public static final String idKey = "id";
    public static final String okKey = "ok";
    public static final String submitterKey = "submitter";
    public static final String submitterParameterKey = "submitterParameter";
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

    @Override
    public String toGroovy() {
        return new StringBuffer()
                .append(directive)
                .append(getDirectiveOpen())
                .append(messageKey)
                .append(this.getStringOpen())
                .append(this.message)
                .append(this.getStringClose())
                .append(this.optionalStringToGroovy(id,idKey))
                .append(this.optionalStringToGroovy(ok,okKey))
                .append(this.optionalStringToGroovy(submitter, submitterKey))
                .append(this.optionalStringToGroovy(submitterParameter,submitterParameterKey))
                .append(this.parametersModel.map(ParametersModel::toGroovy).orElse(""))
                .toString();

    }
}
