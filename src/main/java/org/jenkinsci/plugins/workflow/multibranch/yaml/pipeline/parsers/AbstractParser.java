package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlKeyEmptyException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.KeyValueModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.VariableModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.*;

@Getter
@Setter
public abstract class AbstractParser {

    protected String yamlNodeName = "";
    protected Yaml yaml = new Yaml(new SafeConstructor());

    protected Object getValue(LinkedHashMap parentNode, String key) throws PipelineAsYamlKeyEmptyException {
        if (parentNode.containsKey(key))
            return parentNode.get(key);
        throw new PipelineAsYamlKeyEmptyException();
    }

    protected LinkedHashMap getChildNodeAsLinkedHashMap(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        LinkedHashMap childNode = (LinkedHashMap) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    protected List getChildNodeAsList(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        List childNode = (List) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    protected String getChildNodeAsString(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        String childNode = (String) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    protected Object getChildNodeAsObject(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        Object childNode = parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    protected String getKey(LinkedHashMap node) throws PipelineAsYamlKeyEmptyException {
        Set set = node.keySet();
        Optional key = set.stream().findFirst();
        if (!key.isPresent())
            throw new PipelineAsYamlKeyEmptyException();
        return (String) key.get();
    }

    protected List<KeyValueModel> extractParameters(Object parameter) {
        List<KeyValueModel> extractedParameters = new ArrayList<>();
        if (parameter == null)
            return new ArrayList<>();
        if (parameter instanceof LinkedHashMap) {
            LinkedHashMap agentParameters = (LinkedHashMap) parameter;
            for (Object entry : agentParameters.entrySet()) {
                Map.Entry<String, String> entryMap = (Map.Entry<String, String>) entry;
                KeyValueModel keyValueModel = new KeyValueModel(entryMap.getKey(), entryMap.getValue());
                extractedParameters.add(keyValueModel);
            }
            return extractedParameters;
        }
        if (parameter instanceof String) {
            KeyValueModel keyValueModel = new KeyValueModel("label", (String) parameter);
            extractedParameters.add(keyValueModel);
        }
        return extractedParameters;
    }

    protected List<VariableModel> convert(List<KeyValueModel> keyValueModels) {
        List<VariableModel> variableModelList = new ArrayList<>();
        keyValueModels.forEach(keyValueModel -> {
            variableModelList.add(new VariableModel(keyValueModel.getKey(), keyValueModel.getValue()));
        });
        return variableModelList;
    }

}
