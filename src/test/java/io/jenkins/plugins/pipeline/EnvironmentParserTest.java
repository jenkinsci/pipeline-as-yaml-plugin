package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.EnvironmentModel;
import io.jenkins.plugins.pipeline.models.EnvironmentVariableModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class EnvironmentParserTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Before
    public void setup() {}

    @Test
    public void environmentSingle() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/environment/environmentSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<EnvironmentModel> environmentModel = pipelineModel.get().getEnvironment();
        Assert.assertTrue(environmentModel.isPresent());
        Assert.assertEquals(1, environmentModel.get().getEnvironmentVariables().size());
        for (EnvironmentVariableModel variableModel : environmentModel.get().getEnvironmentVariables()) {
            Assert.assertEquals("KEY1", variableModel.getKey());
            Assert.assertEquals("VAL1", variableModel.getValue());
        }
    }

    @Test
    public void environmentMulti() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/environment/environmentMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<EnvironmentModel> environmentModel = pipelineModel.get().getEnvironment();
        Assert.assertTrue(environmentModel.isPresent());
        Assert.assertEquals(2, environmentModel.get().getEnvironmentVariables().size());
        for (EnvironmentVariableModel variableModel : environmentModel.get().getEnvironmentVariables()) {
            Assert.assertNotNull(variableModel.getKey());
            Assert.assertNotNull(variableModel.getValue());
        }
    }
}
