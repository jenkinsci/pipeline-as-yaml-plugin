package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.InputModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ParametersModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class InputParser extends AbstractParser implements ParserInterface<InputModel> {

    private LinkedHashMap parentNode;
    private String messageKey = "message";
    private String idKey = "id";
    private String okKey = "ok";
    private String submitterKey = "submitter";
    private String submitterParameterKey = "submitterParameter";

    public InputParser(LinkedHashMap parentNode){
        this.yamlNodeName = "input";
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<InputModel> parse() {
        try {
            LinkedHashMap inputNode = this.getChildNodeAsLinkedHashMap(this.parentNode);
            String message = (String) this.getValue(inputNode, this.messageKey);
            Optional<String> id = Optional.ofNullable((String)inputNode.get(this.idKey));
            Optional<String> ok = Optional.ofNullable((String)inputNode.get(this.okKey));
            Optional<String> submitter = Optional.ofNullable((String)inputNode.get(this.submitterKey));
            Optional<String> submitterParameter = Optional.ofNullable((String)inputNode.get(this.submitterParameterKey));
            Optional<ParametersModel> parametersModel = new ParametersParser(inputNode).parse();
            return Optional.of(new InputModel(message,id,ok,submitter,submitterParameter,parametersModel));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
