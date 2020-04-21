package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ScriptModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ScriptParser extends AbstractParser implements ParserInterface<ScriptModel> {

    private LinkedHashMap parentNode;

    public ScriptParser(LinkedHashMap parentNode){
        this.yamlNodeName = ScriptModel.directive;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ScriptModel> parse() {
        try {
            Object scripts = this.parentNode.get(this.yamlNodeName);
            if (scripts instanceof List) {
                return Optional.of(new ScriptModel((List<String>) scripts));
            }
            else if (scripts instanceof String){
                return Optional.of(new ScriptModel((String) scripts));
            }
            else {
                throw new PipelineAsYamlException(String.format("Unexpected type:%s", scripts.getClass().getName()));
            }
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
