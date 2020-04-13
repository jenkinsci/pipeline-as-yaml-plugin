package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.WhenModel;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class WhenParser extends AbstractParser implements ParserInterface<WhenModel> {

    private LinkedHashMap parentNode;

    public WhenParser(LinkedHashMap parentNode){
        this.yamlNodeName = WhenModel.directive;
        this.yaml = new Yaml();
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
