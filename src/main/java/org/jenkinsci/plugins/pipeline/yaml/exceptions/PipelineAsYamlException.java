package org.jenkinsci.plugins.pipeline.yaml.exceptions;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTMethodArg;

import java.util.function.Supplier;
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
