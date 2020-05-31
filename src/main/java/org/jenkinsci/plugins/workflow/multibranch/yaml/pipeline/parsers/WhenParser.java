package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTWhen;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.WhenModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class WhenParser extends AbstractParser implements ParserInterface<WhenModel> {

    public WhenParser(){
        this.yamlNodeName = WhenModel.directive;
    }

    @Override
    public Optional<WhenModel> parse(LinkedHashMap parentNode) {
        try {
            Object whenObject = this.getValue(parentNode, this.yamlNodeName);
            if( whenObject instanceof List) {
                return Optional.of(new WhenModel((List<String>) whenObject));
            }
            else if (whenObject instanceof  LinkedHashMap) {
                return Optional.of(new WhenModel(new WhenConditionalParser().parse((LinkedHashMap) whenObject)));
            }
            return Optional.empty();
        }
        catch (PipelineAsYamlKeyEmptyException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<WhenModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
