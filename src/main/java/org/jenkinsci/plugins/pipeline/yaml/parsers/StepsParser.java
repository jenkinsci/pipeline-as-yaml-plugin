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
    private List<String> stepsAsList;

    public StepsParser(LinkedHashMap parentNode){
        this.yamlNodeName = "steps";
        this.yaml = new Yaml();
        this.parentNode = parentNode;
        this.parseLinkedHashMap = true;
    }

    public StepsParser(List<String> steps){
        this.parseLinkedHashMap = false;
        this.stepsAsList = steps;
    }

    @Override
    public Optional<StepsModel> parse() {
        try {
            if (this.parseLinkedHashMap) {
                List<String> stepsLists = this.getChildNodeAsList(parentNode);
                return Optional.of(new StepsModel(stepsLists));
            } else {
                return Optional.of(new StepsModel(this.stepsAsList));
            }
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
