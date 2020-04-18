package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions;

public class PipelineAsYamlUnexpectedNodeNumber extends PipelineAsYamlException {
    public PipelineAsYamlUnexpectedNodeNumber(String nodeName) {
        super(String.format("%s - defined less/more then expected.", nodeName));
    }


}
