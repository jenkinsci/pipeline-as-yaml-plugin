package io.jenkins.plugins.pipeline;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class PipelineSnippetizerTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Test
    public void convertTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/pipeline/pipelineAllinOne.yml"), StandardCharsets.UTF_8);
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String pipelineDec = pipelineAsYamlSnippetizer.convertToDec(jenkinsFileContent);
        Assert.assertNotNull(pipelineDec);
    }

    @Test
    public void validateTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/pipeline/pipelineAllinOne.yml"), StandardCharsets.UTF_8);
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String validationResponse = pipelineAsYamlSnippetizer.parseAndValidatePay(jenkinsFileContent);
        Assert.assertEquals("Valid", validationResponse);
    }

    // FIXME Add tests also for web ui

}
