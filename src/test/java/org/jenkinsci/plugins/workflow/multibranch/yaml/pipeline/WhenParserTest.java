package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.*;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
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
    public void whenScenario1() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenScenario1.yml"));
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
    public void whenScenario2() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenScenario2.yml"));
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
    public void whenScenario3() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenScenario3.yml"));
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
    public void whenScenario4() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/when/whenScenario4.yml"));
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
