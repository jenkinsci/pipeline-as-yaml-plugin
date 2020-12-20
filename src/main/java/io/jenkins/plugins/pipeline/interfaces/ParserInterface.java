package io.jenkins.plugins.pipeline.interfaces;

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
     * @return Optional of Generic Type
     */
    Optional<T> parse();
}
