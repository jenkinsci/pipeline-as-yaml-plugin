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
    private Boolean parseLinkedHashMap = true;
    private List<String> stepsAsList;

    public StepsParser(LinkedHashMap parentNode){
        this.yamlNodeName = "steps";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }
    public StepsParser(List<String> steps){
        this.parseLinkedHashMap = false;
        this.stepsAsList = steps;
    }

    @Override
    public StepsModel parse() {
        if( this.parseLinkedHashMap){
            LinkedHashMap steps = this.getChildNode(this.parentNode);
            //FIXME implement necessary
        }
        else {
            return new StepsModel(this.stepsAsList);
        }
        return null;
    }
}
