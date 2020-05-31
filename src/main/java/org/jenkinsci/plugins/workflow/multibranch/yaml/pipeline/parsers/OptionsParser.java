package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTOptions;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.OptionsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OptionsParser extends AbstractParser implements ParserInterface<OptionsModel, ModelASTOptions> {

    private List optionsNode;

    public OptionsParser(){
        this.yamlNodeName = OptionsModel.directive;
    }

    @Override
    public Optional<OptionsModel> parse(LinkedHashMap parentNode) {
        try {
            this.optionsNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new OptionsModel(this.optionsNode));
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }

    @Override
    public Optional<OptionsModel> parse(ModelASTOptions modelAST) {
        return Optional.empty();
    }
}
