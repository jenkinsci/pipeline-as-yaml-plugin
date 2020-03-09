package org.jenkinsci.plugins.pipeline.yaml.exceptions;

public class PipelineAsYamlNodeNotFoundException extends RuntimeException {
    public PipelineAsYamlNodeNotFoundException(String nodeName) {
        super(String.format("%s - yaml definition not found.", nodeName));
    }
}
