package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

public class PipelineAsYamlKeyEmptyException extends PipelineAsYamlException {
    public PipelineAsYamlKeyEmptyException() {
        super("Key is not present");
    }
}
