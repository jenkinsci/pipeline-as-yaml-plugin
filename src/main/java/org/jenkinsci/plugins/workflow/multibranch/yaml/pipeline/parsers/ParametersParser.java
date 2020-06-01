package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTBuildParameters;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ParametersModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ParametersParser extends AbstractParser implements ParserInterface<ParametersModel> {

    private List parametersNode;

    public ParametersParser(){
        this.yamlNodeName = ParametersModel.directive;
    }

    @Override
    public Optional<ParametersModel> parse(LinkedHashMap parentNode) {
        try {
            this.parametersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new ParametersModel(this.parametersNode));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ParametersModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }


}
