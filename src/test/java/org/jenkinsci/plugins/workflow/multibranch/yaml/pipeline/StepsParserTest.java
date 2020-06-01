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

public class StepsParserTest {

    @Before
    public void setup() {
    }


    @Test
    public void stepsScenario1() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario1.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
    public void stepsScenario2() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario2.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
    public void stepsScenario3() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario3.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
    public void stepsScenario4() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario4.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stages = pipelineModel.get().getStages();
        Assert.assertTrue(stages.isPresent());
        List<StageModel> stageModelList = stages.get().getStageModelList();
        Assert.assertEquals(1, stageModelList.size());
        Optional<StepsModel> stepsModel = stageModelList.get(0).getStepsModel();
        Assert.assertTrue(stepsModel.isPresent());
        Assert.assertEquals(3,stepsModel.get().getSteps().size());
    }

    @Test
    public void stepsScenario5() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario5.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
        Optional<SubScriptModel> subScriptModel = (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("withAnt", subScriptModel.get().getDirective());
        Assert.assertEquals(1,subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());

    }

    @Test
    public void stepsScenario6() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario6.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
        Optional<SubScriptModel> subScriptModel = (Optional<SubScriptModel>) script.get().getScripts().get(1);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("dir", subScriptModel.get().getDirective());
        Assert.assertEquals(1,subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void stepsScenario7() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario7.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
        Optional<SubScriptModel> subScriptModel = (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("catchError", subScriptModel.get().getDirective());
        Assert.assertEquals(1,subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void stepsScenario8() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/steps/stepsScenario8.yml"));
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
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
        Optional<SubScriptModel> subScriptModel = (Optional<SubScriptModel>) script.get().getScripts().get(0);
        Assert.assertTrue(subScriptModel.isPresent());
        Assert.assertEquals("withEnv", subScriptModel.get().getDirective());
        Assert.assertEquals(2,subScriptModel.get().getScriptModel().getScripts().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

}
