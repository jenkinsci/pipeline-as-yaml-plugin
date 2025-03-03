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

class StepsParserTest {

    @Test
    void stepsScenario1() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/steps/stepsBasic.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        assertEquals(1, stepsModel.get().getSteps().size());
    }

    @Test
    void stepsScenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsBasicWithScript.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(1, script.get().getScripts().size());
    }

    @Test
    void stepsScenario3() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsScriptMultiline.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(3, script.get().getScripts().size());
    }

    @Test
    void stepsScenario4() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsMultiLine.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        assertEquals(3, stepsModel.get().getSteps().size());
    }

    @Test
    void stepsScenario5() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsWithCredentialsAndEnv.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(4, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        assertTrue(subScriptModel.isPresent());
        assertEquals("withAnt", subScriptModel.get().getDirective());
        assertEquals(1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    void stepsScenario6() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/steps/stepsDir.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(2, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(1);
        assertTrue(subScriptModel.isPresent());
        assertEquals("dir", subScriptModel.get().getDirective());
        assertEquals(1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    void stepsScenario7() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsCatchError.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(1, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        assertTrue(subScriptModel.isPresent());
        assertEquals("catchError", subScriptModel.get().getDirective());
        assertEquals(1, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    void stepsScenario8() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/steps/stepsWithEnvAdvanced.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        assertTrue(stepsModel.isPresent());
        Optional<ScriptModel> script = stepsModel.get().getScript();
        assertTrue(script.isPresent());
        assertEquals(1, script.get().getScripts().size());
        Optional<SubScriptModel> subScriptModel =
                (Optional<SubScriptModel>) script.get().getScripts().get(0);
        assertTrue(subScriptModel.isPresent());
        assertEquals("withEnv", subScriptModel.get().getDirective());
        assertEquals(2, subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }
}
