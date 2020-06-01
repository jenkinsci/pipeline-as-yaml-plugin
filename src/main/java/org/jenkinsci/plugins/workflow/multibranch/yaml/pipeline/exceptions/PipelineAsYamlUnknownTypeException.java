package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

public class PipelineAsYamlUnknownTypeException extends PipelineAsYamlException {
    public PipelineAsYamlUnknownTypeException(String classType) {
        super(String.format("%s - type is not defined.", classType));
    }
}
