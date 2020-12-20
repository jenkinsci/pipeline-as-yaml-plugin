package io.jenkins.plugins.pipeline.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception class for unhandled runtime exceptions
 *
 * @see PipelineAsYamlException
 */
public class PipelineAsYamlRuntimeException extends RuntimeException {

    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlException.class.getName());

    public PipelineAsYamlRuntimeException(String s) {
        super(s);
        LOGGER.log(Level.FINE, s, this);
    }

    public PipelineAsYamlRuntimeException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.log(Level.FINE, message, this);
    }
}
