package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class WhenParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void whenScenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenBasic.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        Assert.assertTrue(whenModel.isPresent());
        List<String> whenRuleList = whenModel.get().getWhenRuleList();
        Assert.assertEquals(1, whenRuleList.size());
    }

    @Test
    public void whenScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenAnyOf.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        Assert.assertTrue(whenModel.isPresent());
        Optional<WhenConditionModel> whenConditionModel = whenModel.get().getWhenConditionModel();
        Assert.assertTrue(whenConditionModel.isPresent());
        List<String> whenRuleList = whenConditionModel.get().getWhenRuleList();
        Assert.assertEquals(2, whenRuleList.size());
    }

    @Test
    public void whenScenario3() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenInnerConditions.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        Assert.assertTrue(whenModel.isPresent());
        Optional<WhenConditionModel> whenConditionModel = whenModel.get().getWhenConditionModel();
        Assert.assertTrue(whenConditionModel.isPresent());
        Optional<WhenConditionModel> innerWhenConditionModel = whenConditionModel.get().getWhenConditionModel();
        Assert.assertTrue(innerWhenConditionModel.isPresent());
        List<String> whenRuleList = innerWhenConditionModel.get().getWhenRuleList();
        Assert.assertEquals(2, whenRuleList.size());
    }

    @Test
    public void whenScenario4() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenFlags.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<WhenModel> whenModel = stageModelList.get(0).getWhenModel();
        Assert.assertTrue(whenModel.isPresent());
        Assert.assertEquals(1, whenModel.get().getWhenRuleList().size());
        Assert.assertTrue(whenModel.get().getBeforeAgent().get());
    }
}
