package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PipelineAsYamlRuntimeException extends RuntimeException {
    private static final Logger LOGGER = Logger.getLogger(PipelineAsYamlRuntimeException.class.getName());
    public PipelineAsYamlRuntimeException(String s) {
        super(s);
        LOGGER.log(Level.INFO, s, this);
    }
}
