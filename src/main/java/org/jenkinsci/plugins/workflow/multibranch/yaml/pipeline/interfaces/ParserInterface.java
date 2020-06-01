package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTElement;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;

import java.util.LinkedHashMap;
import java.util.Optional;

public interface ParserInterface<T > {

    Optional<T> parse(LinkedHashMap parentNode);

    Optional<T> parse(ModelASTPipelineDef modelASTPipelineDef);
}
