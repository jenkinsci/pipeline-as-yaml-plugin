package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces;

import java.util.Optional;

public interface ParserInterface<T > {

    Optional<T> parse();
}
