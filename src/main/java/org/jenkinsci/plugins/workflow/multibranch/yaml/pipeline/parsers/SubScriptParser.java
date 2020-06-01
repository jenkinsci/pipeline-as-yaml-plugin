package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTScriptBlock;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ScriptModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.SubScriptModel;

import java.lang.reflect.Array;
import java.util.*;

public class SubScriptParser extends AbstractParser implements ParserInterface<SubScriptModel> {


    public SubScriptParser(){
    }

    @Override
    public Optional<SubScriptModel> parse(LinkedHashMap parentNode) {
        try {
            String directive = this.getKey(parentNode);
            String value = (String) this.getValue(parentNode, directive);
            Optional<ScriptModel> scriptModel = new ScriptParser().parse(parentNode);
            return Optional.of(new SubScriptModel(directive, Optional.ofNullable(value), scriptModel.get()));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SubScriptModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
