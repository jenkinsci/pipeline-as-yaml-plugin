package org.jenkinsci.plugins.pipeline.yaml.exceptions;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTMethodArg;

import java.util.function.Supplier;

public class PipelineAsYamlException extends RuntimeException  {
    public PipelineAsYamlException(String message) {
        super(message);
    }

    public PipelineAsYamlException(Object unKnownClass){
        super(String.format("%s - Unknown class type to parse", unKnownClass.getClass().getName()));
    }

}
