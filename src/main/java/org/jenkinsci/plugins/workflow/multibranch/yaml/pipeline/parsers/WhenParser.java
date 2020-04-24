package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.WhenModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class WhenParser extends AbstractParser implements ParserInterface<WhenModel> {

    private LinkedHashMap parentNode;

    public WhenParser(LinkedHashMap parentNode){
        this.yamlNodeName = WhenModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<WhenModel> parse() {
        try {
            Object whenObject = this.getValue(this.parentNode, this.yamlNodeName);
            if( whenObject instanceof List) {
                return Optional.of(new WhenModel((List<String>) whenObject));
            }
            else if (whenObject instanceof  LinkedHashMap) {
                return Optional.of(new WhenModel(new WhenConditionalParser((LinkedHashMap) whenObject).parse()));
            }
            return Optional.empty();
        }
        catch (PipelineAsYamlKeyEmptyException e) {
            return Optional.empty();
        }

    }
}
