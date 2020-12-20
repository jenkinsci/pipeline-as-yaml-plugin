package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.ScriptModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link ScriptModel}
 */
public class ScriptParser extends AbstractParser implements ParserInterface<ScriptModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public ScriptParser(LinkedHashMap parentNode){
        this.yamlNodeName = ScriptModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ScriptModel> parse() {
        try {
            Object scripts = this.getChildNodeAsObject(parentNode);
            if (scripts instanceof List) {
                ArrayList scriptModelList = new ArrayList();
                for(Object element : (List)scripts) {
                    if( element instanceof String) {
                        scriptModelList.add(element);
                    }
                    else if ( element instanceof LinkedHashMap) {
                        scriptModelList.add(new SubScriptParser((LinkedHashMap) element).parse());
                    }
                }
                return Optional.of(new ScriptModel(scriptModelList));
            }
            else if (scripts instanceof String){
                return Optional.of(new ScriptModel((String) scripts));
            }
            else {
                throw new PipelineAsYamlUnknownTypeException(scripts.getClass().getName());
            }
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
