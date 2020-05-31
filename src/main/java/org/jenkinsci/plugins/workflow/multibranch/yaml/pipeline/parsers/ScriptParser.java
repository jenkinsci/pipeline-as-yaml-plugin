package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTScriptBlock;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ScriptModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ScriptParser extends AbstractParser implements ParserInterface<ScriptModel> {


    public ScriptParser(){
        this.yamlNodeName = ScriptModel.directive;
    }

    @Override
    public Optional<ScriptModel> parse(LinkedHashMap parentNode) {
        try {
            Object scripts = parentNode.get(this.yamlNodeName);
            if (scripts instanceof List) {
                ArrayList scriptModelList = new ArrayList();
                for(Object element : (List)scripts) {
                    if( element instanceof String) {
                        scriptModelList.add(element);
                    }
                    else if ( element instanceof LinkedHashMap) {
                        scriptModelList.add(new SubScriptParser().parse((LinkedHashMap) element));
                    }
                }
                return Optional.of(new ScriptModel(scriptModelList));
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

    @Override
    public Optional<ScriptModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
