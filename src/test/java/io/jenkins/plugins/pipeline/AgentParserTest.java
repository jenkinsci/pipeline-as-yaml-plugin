package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.AgentModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AgentParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void agentAnyTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentAny.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "any");
    }

    @Test
    public void agentNoneTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNone.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "none");
    }

    @Test
    public void agentLabel() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentLabel.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "node");
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentNode() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNode.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "node");
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentDocker() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDocker.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "docker");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    public void agentDockerFile() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDockerfile.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "dockerfile");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    public void agentKubernetes() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentKubernetes.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals("kubernetes", agentModel.get().getAgentType());
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }
}
