package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ScriptModel;
import org.jenkinsci.plugins.pipeline.yaml.models.StepsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StepsParser extends AbstractParser implements ParserInterface<StepsModel> {

    private LinkedHashMap parentNode;
    private Boolean parseLinkedHashMap;
    private List<String> stepsAsList;

    public StepsParser(LinkedHashMap parentNode){
        this.yamlNodeName = "steps";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
        this.parseLinkedHashMap = true;
    }

    public StepsParser(List<String> steps){
        this.parseLinkedHashMap = false;
        this.stepsAsList = steps;
    }

    @Override
    public StepsModel parse() {
        if( this.parseLinkedHashMap){
            List<String> stepsLists = this.getChildNodeAsList(parentNode);
            return new StepsModel(stepsLists);
        }
        else {
            return new StepsModel(this.stepsAsList);
        }
    }
}
