package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class StagesParserTest {

    @Test
    void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stageBasic.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        assertEquals("Stage1", stageModel.getName());
    }

    @Test
    void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesBasicMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(2, stagesModel.get().getStageModelList().size());
    }

    @Test
    void scenarioAgent() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioAgent.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<AgentModel> agentModel = stageModel.getAgentModel();
        assertTrue(agentModel.isPresent());
        assertEquals("none", agentModel.get().getAgentType());
    }

    @Test
    void scenarioPost() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioPost.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<PostModel> postModel = stageModel.getPostModel();
        assertTrue(postModel.isPresent());
        assertEquals(1, postModel.get().getChildPostModels().size());
    }

    @Test
    void scenarioTools() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioTools.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<ToolsModel> toolsModel = stageModel.getToolsModel();
        assertTrue(toolsModel.isPresent());
        assertEquals(1, toolsModel.get().getChildToolModels().size());
    }

    @Test
    void scenarioInnerStages() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioInnerStages.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<StagesModel> innerStagesModel = stageModel.getStagesModel();
        assertTrue(innerStagesModel.isPresent());
        assertEquals(1, innerStagesModel.get().getStageModelList().size());
    }

    @Test
    void scenarioEnvironment() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioEnvironment.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<EnvironmentModel> environmentModel = stageModel.getEnvironmentModel();
        assertTrue(environmentModel.isPresent());
        assertEquals(1, environmentModel.get().getEnvironmentVariables().size());
    }

    @Test
    void scenarioInput() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/stages/stagesScenarioInput.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<StagesModel> stagesModel = pipelineModel.get().getStages();
        assertTrue(stagesModel.isPresent());
        assertEquals(1, stagesModel.get().getStageModelList().size());
        StageModel stageModel = stagesModel.get().getStageModelList().get(0);
        Optional<InputModel> inputModel = stageModel.getInputModel();
        assertTrue(inputModel.isPresent());
        assertEquals("message", inputModel.get().getMessage());
        assertEquals("id", inputModel.get().getId().get());
        assertEquals("ok", inputModel.get().getOk().get());
        assertEquals("submitter", inputModel.get().getSubmitter().get());
        assertEquals(
                "submitterParameter", inputModel.get().getSubmitterParameter().get());
        Optional<ParametersModel> parametersModel = inputModel.get().getParametersModel();
        assertTrue(parametersModel.isPresent());
        assertEquals(1, parametersModel.get().getParametersList().size());
    }
}
