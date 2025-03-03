package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class WhenParserTest {

    @Test
    void whenScenario1() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/when/whenBasic.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        assertTrue(whenModel.isPresent());
        List<String> whenRuleList = whenModel.get().getWhenRuleList();
        assertEquals(1, whenRuleList.size());
    }

    @Test
    void whenScenario2() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/when/whenAnyOf.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        assertTrue(whenModel.isPresent());
        Optional<WhenConditionModel> whenConditionModel = whenModel.get().getWhenConditionModel();
        assertTrue(whenConditionModel.isPresent());
        List<String> whenRuleList = whenConditionModel.get().getWhenRuleList();
        assertEquals(2, whenRuleList.size());
    }

    @Test
    void whenScenario3() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/when/whenInnerConditions.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        assertTrue(whenModel.isPresent());
        Optional<WhenConditionModel> whenConditionModel = whenModel.get().getWhenConditionModel();
        assertTrue(whenConditionModel.isPresent());
        Optional<WhenConditionModel> innerWhenConditionModel =
                whenConditionModel.get().getWhenConditionModel();
        assertTrue(innerWhenConditionModel.isPresent());
        List<String> whenRuleList = innerWhenConditionModel.get().getWhenRuleList();
        assertEquals(2, whenRuleList.size());
    }

    @Test
    void whenScenario4() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/when/whenFlags.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        assertTrue(whenModel.isPresent());
        assertEquals(1, whenModel.get().getWhenRuleList().size());
        assertTrue(whenModel.get().getBeforeAgent().get());
    }
}
