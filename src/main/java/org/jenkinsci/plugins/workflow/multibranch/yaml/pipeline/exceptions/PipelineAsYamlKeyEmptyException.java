package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

/**
 * Exception class for handling empty key in LinkedHashMaps
 *
 * @see PipelineAsYamlException
 */
public class PipelineAsYamlKeyEmptyException extends PipelineAsYamlException {
    public PipelineAsYamlKeyEmptyException() {
        super("Key is not present");
    }
}
