package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.ParallelModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.models.StageModel;
import io.jenkins.plugins.pipeline.models.StagesModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ParallelParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void parallelScenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parallel/parallelBasic.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(2, stageModelList.size());
        StageModel stageModel = stageModelList.get(1);
        Optional<ParallelModel> parallelModel = stageModel.getParallelModel();
        Assert.assertTrue(parallelModel.isPresent());
        List<StageModel> parallelStageModels = parallelModel.get().getStageModelList();
        Assert.assertEquals(2, parallelStageModels.size());

    }

    @Test
    public void parallelScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parallel/parallelFailfast.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(2, stageModelList.size());
        StageModel stageModel = stageModelList.get(1);
        Assert.assertTrue(stageModel.getFailFast().isPresent());
        Assert.assertTrue(stageModel.getFailFast().get());
        Optional<ParallelModel> parallelModel = stageModel.getParallelModel();
        Assert.assertTrue(parallelModel.isPresent());
        List<StageModel> parallelStageModels = parallelModel.get().getStageModelList();
        Assert.assertEquals(2, parallelStageModels.size());
    }

}
