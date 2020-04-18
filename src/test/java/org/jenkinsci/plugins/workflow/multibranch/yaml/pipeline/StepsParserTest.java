package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StepsParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void stepsScenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario1.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Assert.assertEquals(1,stepsModel.get().getSteps().size());
    }

    @Test
    public void stepsScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario2.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        Assert.assertTrue(script.isPresent());
        Assert.assertEquals(1, script.get().getScripts().size());
    }

    @Test
    public void stepsScenario3() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario3.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        Assert.assertTrue(script.isPresent());
        Assert.assertEquals(3, script.get().getScripts().size());
    }

    @Test
    public void stepsScenario4() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario4.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Assert.assertEquals(3,stepsModel.get().getSteps().size());
    }
}
