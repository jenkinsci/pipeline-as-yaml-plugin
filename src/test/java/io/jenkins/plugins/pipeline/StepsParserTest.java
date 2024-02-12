package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StepsParserTest {

    @Before
    public void setup() {}

    @Test
    public void stepsScenario1() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/steps/stepsBasic.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Assert.assertEquals(1, stepsModel.get().getSteps().size());
    }

    @Test
    public void stepsScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsBasicWithScript.yml"), StandardCharsets.UTF_8);
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
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsScriptMultiline.yml"), StandardCharsets.UTF_8);
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
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsMultiLine.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Assert.assertEquals(3, stepsModel.get().getSteps().size());
    }

    @Test
    public void stepsScenario5() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsWithCredentialsAndEnv.yml"), StandardCharsets.UTF_8);
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
        Assert.assertEquals(4, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("withAnt", subScriptModel.get().getDirective());
        Assert.assertEquals(
                1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void stepsScenario6() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/steps/stepsDir.yml"), StandardCharsets.UTF_8);
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
        Assert.assertEquals(2, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(1);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("dir", subScriptModel.get().getDirective());
        Assert.assertEquals(
                1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void stepsScenario7() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsCatchError.yml"), StandardCharsets.UTF_8);
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
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("catchError", subScriptModel.get().getDirective());
        Assert.assertEquals(
                1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void stepsScenario8() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsWithEnvAdvanced.yml"), StandardCharsets.UTF_8);
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
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("withEnv", subScriptModel.get().getDirective());
        Assert.assertEquals(
                2, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }
}
