package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.models.TriggersModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TriggersParserTest {

    @Before
    public void setup() {}

    @Test
    public void triggersSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/triggers/triggersSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        Assert.assertTrue(triggersModel.isPresent());
        Assert.assertEquals(1, triggersModel.get().getTriggersList().size());
    }

    @Test
    public void triggersMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/triggers/triggersMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        Assert.assertTrue(triggersModel.isPresent());
        Assert.assertEquals(2, triggersModel.get().getTriggersList().size());
    }
}
