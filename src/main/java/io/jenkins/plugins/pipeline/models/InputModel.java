package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline Input Section
 */
@Getter
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

    /**
     * @param message Input message
     * @param id Input id
     * @param ok Input ok message
     * @param submitter Input submitter
     * @param submitterParameter Input submitter parameter
     * @param parametersModel Input {@link ParametersModel}
     */
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
                .append(getDirectiveClose())
                .toString();

    }
}
