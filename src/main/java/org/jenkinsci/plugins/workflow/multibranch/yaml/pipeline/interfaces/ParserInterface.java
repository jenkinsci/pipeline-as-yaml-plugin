package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTElement;

import java.util.LinkedHashMap;
import java.util.Optional;

public interface ParserInterface<T, M extends ModelASTElement > {

    Optional<T> parse(LinkedHashMap parentNode);

    Optional<T> parse(M modelAST);
}
