package org.jenkinsci.plugins.pipeline.yaml.parsers;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlUnexpectedNodeNumber;
import org.jenkinsci.plugins.pipeline.yaml.models.KeyValueModel;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

@Getter
@Setter
public abstract class AbstractParser {

    protected boolean nodeRequired = false;
    protected String yamlNodeName = "";
    protected Yaml yaml;
    protected int expectedSize = 1;

    protected LinkedHashMap checkExpectedSite(LinkedHashMap node){
        if( node.size() != this.expectedSize ) {
            throw new PipelineAsYamlUnexpectedNodeNumber(this.yamlNodeName);
        }
        return node;
    }

    protected Object getChildNode(LinkedHashMap parentNode ) {
        if (this.isNodeRequired() && !parentNode.containsKey(this.yamlNodeName)) {
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        }
        Object childNode =  parentNode.get(this.yamlNodeName);
        if( childNode instanceof LinkedHashMap)
            return (LinkedHashMap)childNode;
        else if( childNode instanceof List)
            return (List)childNode;
        else
            return new LinkedHashMap<>();
    }

    protected LinkedHashMap getChildNode(String key, LinkedHashMap node) {
        return (LinkedHashMap) node.get(key);
    }

    protected String getKey(LinkedHashMap node){
        Set set = node.keySet();
        Optional key =  set.stream().findFirst();
        return (String) key.get();
    }

    protected List<String> getKeyList(LinkedHashMap node){
        ArrayList<String> keyList = new ArrayList<>();
        Set<String> set = node.keySet();
        set.forEach(o -> keyList.add(o.toString()));
        return keyList;
    }

    protected List<KeyValueModel> extractParameters(Object parameter) {
        List<KeyValueModel> extractedParameters = new ArrayList<>();
        if (parameter == null)
            return new ArrayList<>();
        if(parameter instanceof LinkedHashMap) {
            LinkedHashMap agentParameters = (LinkedHashMap) parameter;
            for (Object entry : agentParameters.entrySet()) {
                Map.Entry<String, String> entryMap = (Map.Entry<String, String>) entry;
                KeyValueModel keyValueModel = new KeyValueModel(entryMap.getKey(), entryMap.getValue());
                extractedParameters.add(keyValueModel);
            }
            return extractedParameters;
        }
        if(parameter instanceof String){
            KeyValueModel keyValueModel = new KeyValueModel("label", (String) parameter);
            extractedParameters.add(keyValueModel);
        }
        return extractedParameters;
    }

}
