package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class StagesParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stageBasic.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Assert.assertEquals("Stage1", stageModel.getName());
    }

    @Test
    public void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesBasicMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(2, stagesModel.get().getStageModelList().size());
    }


    @Test
    public void scenarioAgent() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioAgent.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<AgentModel> agentModel = stageModel.getAgentModel();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(),"none");
    }

    @Test
    public void scenarioPost() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioPost.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<PostModel> postModel = stageModel.getPostModel();
        Assert.assertTrue(postModel.isPresent());
        Assert.assertEquals(1, postModel.get().getChildPostModels().size());
    }

    @Test
    public void scenarioTools() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioTools.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<ToolsModel> toolsModel = stageModel.getToolsModel();
        Assert.assertTrue(toolsModel.isPresent());
        Assert.assertEquals(1, toolsModel.get().getChildToolModels().size());
    }

    @Test
    public void scenarioInnerStages() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioInnerStages.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<StagesModel> innerStagesModel = stageModel.getStagesModel();
        Assert.assertTrue(innerStagesModel.isPresent());
        Assert.assertEquals(1, innerStagesModel.get().getStageModelList().size());
    }

    @Test
    public void scenarioEnvironment() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioEnvironment.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<EnvironmentModel> environmentModel = stageModel.getEnvironmentModel();
        Assert.assertTrue(environmentModel.isPresent());
        Assert.assertEquals(1, environmentModel.get().getEnvironmentVariables().size());
    }

    @Test
    public void scenarioInput() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenarioInput.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        Assert.assertTrue(stagesModel.isPresent());
        Assert.assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<InputModel> inputModel = stageModel.getInputModel();
        Assert.assertTrue(inputModel.isPresent());
        Assert.assertEquals("message", inputModel.get().getMessage());
        Assert.assertEquals("id", inputModel.get().getId().get());
        Assert.assertEquals("ok", inputModel.get().getOk().get());
        Assert.assertEquals("submitter", inputModel.get().getSubmitter().get());
        Assert.assertEquals("submitterParameter", inputModel.get().getSubmitterParameter().get());
        Optional<ParametersModel> parametersModel = inputModel.get().getParametersModel();
        Assert.assertTrue(parametersModel.isPresent());
        Assert.assertEquals(1, parametersModel.get().getParametersList().size());
    }

}

