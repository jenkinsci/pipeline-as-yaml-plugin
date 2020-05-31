package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStageInput;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.InputModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ParametersModel;

import java.util.LinkedHashMap;
import java.util.Optional;

public class InputParser extends AbstractParser implements ParserInterface<InputModel, ModelASTStageInput> {

    private String messageKey = InputModel.messageKey;
    private String idKey = InputModel.idKey;
    private String okKey = InputModel.okKey;
    private String submitterKey = InputModel.submitterKey;
    private String submitterParameterKey = InputModel.submitterParameterKey;

    public InputParser(){
        this.yamlNodeName = InputModel.directive;
    }

    @Override
    public Optional<InputModel> parse(LinkedHashMap parentNode) {
        try {
            LinkedHashMap inputNode = this.getChildNodeAsLinkedHashMap(parentNode);
            String message = (String) this.getValue(inputNode, this.messageKey);
            Optional<String> id = Optional.ofNullable((String)inputNode.get(this.idKey));
            Optional<String> ok = Optional.ofNullable((String)inputNode.get(this.okKey));
            Optional<String> submitter = Optional.ofNullable((String)inputNode.get(this.submitterKey));
            Optional<String> submitterParameter = Optional.ofNullable((String)inputNode.get(this.submitterParameterKey));
            Optional<ParametersModel> parametersModel = new ParametersParser().parse(inputNode);
            return Optional.of(new InputModel(message,id,ok,submitter,submitterParameter,parametersModel));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<InputModel> parse(ModelASTStageInput modelAST) {
        return Optional.empty();
    }
}
