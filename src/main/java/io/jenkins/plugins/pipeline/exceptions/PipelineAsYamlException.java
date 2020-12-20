package io.jenkins.plugins.pipeline.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Exception class for handling exceptions in the plugin
 */
public class PipelineAsYamlException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlException.class.getName());

    public PipelineAsYamlException(String message) {
        super(message);
        LOGGER.log(Level.FINE, message, this);
    }
}
