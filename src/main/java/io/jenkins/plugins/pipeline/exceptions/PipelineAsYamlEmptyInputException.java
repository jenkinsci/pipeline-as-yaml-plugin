package io.jenkins.plugins.pipeline.exceptions;

import java.util.logging.Logger;

/**
 * Exception class for handling Empty Input from Converter
 *
 * @see PipelineAsYamlException
 */
public class PipelineAsYamlEmptyInputException extends PipelineAsYamlException {

    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlEmptyInputException.class.getName());

    public PipelineAsYamlEmptyInputException(String message) {
        super(message);
        LOGGER.fine(message);
    }

}
