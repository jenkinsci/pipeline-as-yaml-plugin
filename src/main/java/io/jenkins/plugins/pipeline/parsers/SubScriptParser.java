package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.ScriptModel;
import io.jenkins.plugins.pipeline.models.SubScriptModel;

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
