package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.ParallelModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.models.StageModel;
import io.jenkins.plugins.pipeline.models.StagesModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class ParallelParserTest {

    @Test
    void parallelScenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/parallel/parallelBasic.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(2, stageModelList.size());
        StageModel stageModel = stageModelList.get(1);
        Optional<ParallelModel> parallelModel = stageModel.getParallelModel();
        assertTrue(parallelModel.isPresent());
        List<StageModel> parallelStageModels = parallelModel.get().getStageModelList();
        assertEquals(2, parallelStageModels.size());
    }

    @Test
    void parallelScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/parallel/parallelFailfast.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(2, stageModelList.size());
        StageModel stageModel = stageModelList.get(1);
        assertTrue(stageModel.getFailFast().isPresent());
        assertTrue(stageModel.getFailFast().get());
        Optional<ParallelModel> parallelModel = stageModel.getParallelModel();
        assertTrue(parallelModel.isPresent());
        List<StageModel> parallelStageModels = parallelModel.get().getStageModelList();
        assertEquals(2, parallelStageModels.size());
    }
}
