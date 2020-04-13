package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.StepsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class StepsParser extends AbstractParser implements ParserInterface<StepsModel> {

    private LinkedHashMap parentNode;
    private Boolean parseLinkedHashMap;

    public StepsParser(LinkedHashMap parentNode){
        this.yamlNodeName = StepsModel.directive;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    public StepsParser(List stepsList){
        this.yamlNodeName = StepsModel.directive;
        this.yaml = new Yaml();
        LinkedHashMap tempLinkedHashMap = new LinkedHashMap();
        tempLinkedHashMap.put(this.yamlNodeName, stepsList);
        this.parentNode = tempLinkedHashMap;
    }

    @Override
    public Optional<StepsModel> parse() {
        Object stepsNode = this.parentNode.get(this.yamlNodeName);
        if (stepsNode instanceof LinkedHashMap) {
            return Optional.of(new StepsModel(new ScriptParser((LinkedHashMap) stepsNode).parse()));
        }
        else if( stepsNode instanceof  String) {
            return Optional.of(new StepsModel((String) stepsNode));
        }
        else {
            return Optional.of(new StepsModel((List) stepsNode));
        }
    }
}
