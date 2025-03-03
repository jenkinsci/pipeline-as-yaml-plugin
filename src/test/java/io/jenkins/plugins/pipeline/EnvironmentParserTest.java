package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.EnvironmentModel;
import io.jenkins.plugins.pipeline.models.EnvironmentVariableModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class EnvironmentParserTest {

    @Test
    void environmentSingle(JenkinsRule jenkins) throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/environment/environmentSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<EnvironmentModel> environmentModel = pipelineModel.get().getEnvironment();
        assertTrue(environmentModel.isPresent());
        assertEquals(1, environmentModel.get().getEnvironmentVariables().size());
        for (EnvironmentVariableModel variableModel : environmentModel.get().getEnvironmentVariables()) {
            assertEquals("KEY1", variableModel.getKey());
            assertEquals("VAL1", variableModel.getValue());
        }
    }

    @Test
    void environmentMulti(JenkinsRule jenkins) throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/environment/environmentMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<EnvironmentModel> environmentModel = pipelineModel.get().getEnvironment();
        assertTrue(environmentModel.isPresent());
        assertEquals(2, environmentModel.get().getEnvironmentVariables().size());
        for (EnvironmentVariableModel variableModel : environmentModel.get().getEnvironmentVariables()) {
            assertNotNull(variableModel.getKey());
            assertNotNull(variableModel.getValue());
        }
    }
}
