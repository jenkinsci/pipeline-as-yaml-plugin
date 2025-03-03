package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.models.TriggersModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class TriggersParserTest {

    @Test
    void triggersSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/triggers/triggersSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        assertTrue(triggersModel.isPresent());
        assertEquals(1, triggersModel.get().getTriggersList().size());
    }

    @Test
    void triggersMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/triggers/triggersMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        assertTrue(triggersModel.isPresent());
        assertEquals(2, triggersModel.get().getTriggersList().size());
    }
}
