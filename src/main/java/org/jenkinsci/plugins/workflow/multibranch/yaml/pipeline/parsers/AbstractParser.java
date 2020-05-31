package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.*;
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
        if(parentNode.containsKey(key) )
            return parentNode.get(key);
        throw new PipelineAsYamlKeyEmptyException();
    }

    protected LinkedHashMap getChildNodeAsLinkedHashMap(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        LinkedHashMap childNode = (LinkedHashMap) parentNode.get(this.yamlNodeName);
        if( childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
    }

    protected List getChildNodeAsList(LinkedHashMap parentNode) throws PipelineAsYamlNodeNotFoundException {
        List childNode = (List) parentNode.get(this.yamlNodeName);
        if( childNode == null)
            throw new PipelineAsYamlNodeNotFoundException(this.yamlNodeName);
        return childNode;
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
        set.forEach(o -> keyList.add(o));
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

    protected List<VariableModel> convert(List<KeyValueModel> keyValueModels) {
        List<VariableModel> variableModelList = new ArrayList<>();
        keyValueModels.forEach(keyValueModel -> {
            variableModelList.add(new VariableModel(keyValueModel.getKey(), keyValueModel.getValue()));
        });
        return variableModelList;
    }

    protected List<KeyValueModel> convertKeyValueModel(ModelASTElement modelASTElement) {
        List<KeyValueModel> keyValueModels = new ArrayList<>();
        if( modelASTElement instanceof ModelASTAgent) {
            ModelASTAgent modelASTAgent = (ModelASTAgent) modelASTElement;
            if (modelASTAgent.getVariables() instanceof ModelASTClosureMap) {
                ModelASTClosureMap modelASTClosureMap = (ModelASTClosureMap) modelASTAgent.getVariables();
                Map<ModelASTKey, ModelASTMethodArg> variables = modelASTClosureMap.getVariables();
                for (Map.Entry<ModelASTKey, ModelASTMethodArg> entry : variables.entrySet()) {
                    String key = entry.getKey().getKey();
                    String value = (String) ((ModelASTValue) entry.getValue()).getValue();
                    keyValueModels.add(new KeyValueModel(key, value));
                }
            } else if (modelASTAgent.getVariables() instanceof ModelASTValue) {
                ModelASTValue modelASTValue = (ModelASTValue) modelASTAgent.getVariables();
                String key = modelASTAgent.getAgentType().getKey();
                String value = (String) modelASTValue.getValue();
                keyValueModels.add(new KeyValueModel(key, value));
            }
        }
        return keyValueModels;
    }

    protected List<VariableModel> convertVariableModel(ModelASTElement modelASTElement) {
        List<VariableModel> variableModels = new ArrayList<>();
        if(modelASTElement instanceof  ModelASTEnvironment){
            ModelASTEnvironment modelASTEnvironment = (ModelASTEnvironment) modelASTElement;
            Map<ModelASTKey, ModelASTEnvironmentValue> variables = modelASTEnvironment.getVariables();
            for(Map.Entry<ModelASTKey, ModelASTEnvironmentValue> entry : variables.entrySet()) {
                ModelASTKey modelASTKey = entry.getKey();
                String key = modelASTKey.getKey();
                if( entry.getValue() instanceof ModelASTValue) {
                    ModelASTValue modelASTValue = (ModelASTValue) entry.getValue();
                    String value = (String) modelASTValue.getValue();
                    variableModels.add(new VariableModel(key, value));
                }
                else if( entry.getValue() instanceof ModelASTInternalFunctionCall) {
                    ModelASTInternalFunctionCall modelASTInternalFunctionCall = (ModelASTInternalFunctionCall) entry.getValue();
                    String value = this.convertFunction(modelASTInternalFunctionCall);
                    variableModels.add(new VariableModel(key, value));
                }
            }
        }
        return variableModels;
    }

    private String convertFunction(ModelASTInternalFunctionCall modelASTInternalFunctionCall) {
        String methodName = modelASTInternalFunctionCall.getName();
        List<ModelASTValue> args = modelASTInternalFunctionCall.getArgs();
        List<String> arguments = new ArrayList<>();
        for(ModelASTValue modelASTValue : args) {
            arguments.add("'" + (String) modelASTValue.getValue() + "'");
        }
        String argAsString = String.join(",", arguments);
        methodName = methodName + "(" + argAsString + ")";
        return methodName;
    }



}
