package org.jenkinsci.plugins.pipeline.yaml.parsers;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlKeyEmptyException;
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

    protected LinkedHashMap checkExpectedSite(LinkedHashMap node) throws PipelineAsYamlUnexpectedNodeNumber {
        if( node.size() != this.expectedSize ) {
            throw new PipelineAsYamlUnexpectedNodeNumber(this.yamlNodeName);
        }
        return node;
    }

    protected void checkNodeIsRequired(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        if (this.isNodeRequired() && !parentNode.containsKey(this.yamlNodeName)) {
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        }
    }

    protected LinkedHashMap getChildNodeAsLinkedHashMap(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        this.checkNodeIsRequired(parentNode);
        LinkedHashMap childNode = (LinkedHashMap) parentNode.get(this.yamlNodeName);
        if( childNode == null)
            return new LinkedHashMap();
        return childNode;
    }

    protected List getChildNodeAsList(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        this.checkNodeIsRequired(parentNode);
        List childNode = (List) parentNode.get(this.yamlNodeName);
        if( childNode == null)
            return new ArrayList();
        return childNode;
    }

    protected LinkedHashMap getChildNode(String key, LinkedHashMap node) {
        return (LinkedHashMap) node.get(key);
    }

    protected String getKey(LinkedHashMap node) throws PipelineAsYamlKeyEmptyException {
        Set set = node.keySet();
        Optional key =  set.stream().findFirst();
        if( !key.isPresent())
            throw new PipelineAsYamlKeyEmptyException();
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
