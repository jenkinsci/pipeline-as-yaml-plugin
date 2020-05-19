package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

import java.util.logging.Logger;

public class PipelineAsYamlEmptyInputException extends PipelineAsYamlException  {

    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlEmptyInputException.class.getName());

    public PipelineAsYamlEmptyInputException(String message) {
        super(message);
        LOGGER.fine(message);
    }

}
