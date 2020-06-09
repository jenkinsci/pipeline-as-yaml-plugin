package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces;

import java.util.Optional;

/**
 * Interface for Parser classes
 *
 * @param <T> Generic Type
 */
public interface ParserInterface<T> {

    /**
     * Parse Yaml to Model
     *
     * @return {@link Optional<T>} Optional of Generic Type
     */
    Optional<T> parse();
}
