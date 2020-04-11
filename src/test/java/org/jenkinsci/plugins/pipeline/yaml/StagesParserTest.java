package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
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
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenario1.yml"));
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
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/stages/stagesScenario2.yml"));
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

}
