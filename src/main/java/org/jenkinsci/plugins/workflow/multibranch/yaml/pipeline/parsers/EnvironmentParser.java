package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTEnvironment;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.EnvironmentModel;

import java.util.LinkedHashMap;
import java.util.Optional;

public class EnvironmentParser extends AbstractParser implements ParserInterface<EnvironmentModel> {

    private LinkedHashMap environmentNode;

    public EnvironmentParser(){
        this.yamlNodeName = EnvironmentModel.directive;
    }

    @Override
    public Optional<EnvironmentModel> parse(LinkedHashMap parentNode) {
        try {
            this.environmentNode = this.getChildNodeAsLinkedHashMap(parentNode);
            return Optional.of(new EnvironmentModel(this.convert(this.extractParameters(this.environmentNode))));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<EnvironmentModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        ModelASTEnvironment modelASTEnvironment = modelASTPipelineDef.getEnvironment();
        EnvironmentModel environmentModel = new EnvironmentModel(this.convertVariableModel(modelASTEnvironment));
        return Optional.of(environmentModel);
    }


}
