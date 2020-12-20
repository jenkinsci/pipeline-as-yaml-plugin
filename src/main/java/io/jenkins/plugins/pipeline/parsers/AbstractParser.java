package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlKeyEmptyException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import io.jenkins.plugins.pipeline.models.*;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;

/**
 * Abstract parser class which Parsers extends
 */
@Getter
@Setter
public abstract class AbstractParser {

    protected String yamlNodeName = "";
    protected Yaml yaml;
    public AbstractParser() {
        Representer representer = new Representer();
        representer.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(new SafeConstructor(), representer);
    }

    /**
     * Get Value from a {@link LinkedHashMap} with a given key
     * @param parentNode Parent map to get value from
     * @param key Key to get it's value
     * @return Value of the key
     * @throws PipelineAsYamlKeyEmptyException if key is empty
     */
    protected Object getValue(LinkedHashMap parentNode, String key) throws PipelineAsYamlKeyEmptyException {
        if (parentNode.containsKey(key))
            return parentNode.get(key);
        throw new PipelineAsYamlKeyEmptyException();
    }

    /**
     * Get child node of the parent node as {@link LinkedHashMap}. Key is yamlNodeName
     * @param parentNode Parent Map
     * @return Child node as {@link LinkedHashMap} retrieved with yamlNodeName
     * @throws PipelineAsYamlNodeNotFoundException if child node is not found in parent node
     */
    protected LinkedHashMap getChildNodeAsLinkedHashMap(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        LinkedHashMap childNode = (LinkedHashMap) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    /**
     * Get child node of the parent node as {@link List}. Key is yamlNodeName
     * @param parentNode Parent Map
     * @return Child node as {@link List} retrieved with yamlNodeName
     * @throws PipelineAsYamlNodeNotFoundException if child node is not found in parent node
     */
    protected List getChildNodeAsList(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        List childNode = (List) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    /**
     * Get child node of the parent node as {@link String}. Key is yamlNodeName
     * @param parentNode Parent Map
     * @return Child node as {@link String} retrieved with yamlNodeName
     * @throws PipelineAsYamlNodeNotFoundException if child node is not found in parent node
     */
    protected String getChildNodeAsString(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        String childNode = (String) parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    /**
     * Get child node of the parent node as {@link Object}. Key is yamlNodeName
     * @param parentNode Parent Map
     * @return Child node as {@link Object} retrieved with yamlNodeName
     * @throws PipelineAsYamlNodeNotFoundException if child node is not found in parent node
     */
    protected Object getChildNodeAsObject(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        Object childNode = parentNode.get(this.yamlNodeName);
        if (childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    /**
     * Get key name from Node
     * @param node Node to extract key from
     * @return Key Name
     * @throws PipelineAsYamlKeyEmptyException if key is empty
     */
    protected String getKey(LinkedHashMap node) throws PipelineAsYamlKeyEmptyException {
        Set set = node.keySet();
        Optional key = set.stream().findFirst();
        if (!key.isPresent())
            throw new PipelineAsYamlKeyEmptyException();
        return (String) key.get();
    }

    /**
     * Extract parameters from {@link LinkedHashMap} or {@link String} for using in {@link AgentModel}/{@link EnvironmentModel}
     * @param parameter Parameter to be converted
     * @return List of {@link KeyValueModel}
     */
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

    /**
     * Convert List of {@link KeyValueModel} to List of {@link VariableModel}
     * @param keyValueModels List of {@link KeyValueModel} to be converted
     * @return List of {@link VariableModel}
     */
    protected List<VariableModel> convertVariableModel(List<KeyValueModel> keyValueModels) {
        List<VariableModel> variableModelList = new ArrayList<>();
        keyValueModels.forEach(keyValueModel -> {
            variableModelList.add(new VariableModel(keyValueModel.getKey(), keyValueModel.getValue()));
        });
        return variableModelList;
    }

    protected List<EnvironmentVariableModel> convertEnvironmentVariableModel(List<KeyValueModel> keyValueModels) {
        List<EnvironmentVariableModel> variableModelList = new ArrayList<>();
        keyValueModels.forEach(keyValueModel -> {
            variableModelList.add(new EnvironmentVariableModel(keyValueModel.getKey(), keyValueModel.getValue()));
        });
        return variableModelList;
    }

}
