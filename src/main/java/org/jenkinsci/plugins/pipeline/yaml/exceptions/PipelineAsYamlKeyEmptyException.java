package org.jenkinsci.plugins.pipeline.yaml.exceptions;

public class PipelineAsYamlKeyEmptyException extends PipelineAsYamlException {
    public PipelineAsYamlKeyEmptyException() {
        super("Key is not present");
    }
}
