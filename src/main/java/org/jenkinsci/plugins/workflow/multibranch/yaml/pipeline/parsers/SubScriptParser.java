package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.apache.ivy.util.StringUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlRuntimeException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ScriptModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.SubScriptModel;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Parser for {@link SubScriptModel}
 */
public class SubScriptParser extends AbstractParser implements ParserInterface<SubScriptModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public SubScriptParser(LinkedHashMap parentNode){
        this.parentNode = parentNode;
    }

    @Override
    public Optional<SubScriptModel> parse() {
        try {
            String directive = this.getKey(this.parentNode);
            String value = (String) this.getValue(this.parentNode, directive);
            Optional<ScriptModel> scriptModel = new ScriptParser(this.parentNode).parse();
            return Optional.of(new SubScriptModel(directive, Optional.ofNullable(value), scriptModel.get()));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
