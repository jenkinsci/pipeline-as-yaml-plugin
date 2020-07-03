package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.*;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PipelineSnippetizerTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Test
    public void convertTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/pipeline/pipeline1.yml"));
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String pipelineDec = pipelineAsYamlSnippetizer.convertToDec(jenkinsFileContent);
        Assert.assertNotNull(pipelineDec);
    }

    @Test
    public void validateTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/pipeline/pipeline1.yml"));
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String validationResponse = pipelineAsYamlSnippetizer.parseAndValidatePay(jenkinsFileContent);
        Assert.assertEquals("Valid", validationResponse);
    }

    //FIXME Add tests also for web ui

}
