package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class PipelineSnippetizerTest {

    @Test
    void convertTest(JenkinsRule jenkins) throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/pipeline/pipelineAllinOne.yml"), StandardCharsets.UTF_8);
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String pipelineDec = pipelineAsYamlSnippetizer.convertToDec(jenkinsFileContent);
        assertNotNull(pipelineDec);
    }

    @Test
    void validateTest(JenkinsRule jenkins) throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/pipeline/pipelineAllinOne.yml"), StandardCharsets.UTF_8);
        PipelineAsYamlSnippetizer pipelineAsYamlSnippetizer = new PipelineAsYamlSnippetizer();
        String validationResponse = pipelineAsYamlSnippetizer.parseAndValidatePay(jenkinsFileContent);
        assertEquals("Valid", validationResponse);
    }

    // FIXME Add tests also for web ui

}
