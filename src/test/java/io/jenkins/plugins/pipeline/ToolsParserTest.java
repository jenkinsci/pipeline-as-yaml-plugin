package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.ChildToolModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.models.ToolsModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class ToolsParserTest {

    @Test
    void toolsSingle() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/tools/toolsSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<ToolsModel> toolsModel = pipelineModel.get().getTools();
        assertTrue(toolsModel.isPresent());
        assertEquals(1, toolsModel.get().getChildToolModels().size());
        for (ChildToolModel childToolModel : toolsModel.get().getChildToolModels()) {
            assertEquals("maven", childToolModel.getToolName());
            assertEquals("maven", childToolModel.getToolType());
        }
    }

    @Test
    void toolsMulti() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/tools/toolsMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<ToolsModel> toolsModel = pipelineModel.get().getTools();
        assertTrue(toolsModel.isPresent());
        assertEquals(2, toolsModel.get().getChildToolModels().size());
        for (ChildToolModel childToolModel : toolsModel.get().getChildToolModels()) {
            assertEquals(childToolModel.getToolType(), childToolModel.getToolName());
        }
    }
}
