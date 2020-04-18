package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

import java.util.logging.Logger;

public class PipelineAsYamlException extends Exception  {

    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlException.class.getName());

    public PipelineAsYamlException(String message) {
        super(message);
        LOGGER.warning(message);
    }

    public PipelineAsYamlException(Object unKnownClass){
        super(String.format("%s - Unknown class type to parse", unKnownClass.getClass().getName()));
        LOGGER.warning(String.format("%s - Unknown class type to parse", unKnownClass.getClass().getName()));
    }

}
