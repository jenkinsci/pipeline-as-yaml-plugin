package io.jenkins.plugins.pipeline.exceptions;

/**
 * Exception class for handling unknown instance type check in parsers
 *
 * @see PipelineAsYamlException
 */
public class PipelineAsYamlUnknownTypeException extends PipelineAsYamlException {
    public PipelineAsYamlUnknownTypeException(String classType) {
        super(String.format("%s - type is not defined.", classType));
    }
}
