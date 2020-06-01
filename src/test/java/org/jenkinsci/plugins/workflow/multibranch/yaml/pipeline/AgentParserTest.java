package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.AgentModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
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
    public void agentAnyTest() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentAny.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "any");
    }

    @Test
    public void agentNoneTest() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNone.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "none");
    }

    @Test
    public void agentLabel() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentLabel.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "node");
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentNode() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNode.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "node");
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.get().getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentDocker() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDocker.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "docker");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    public void agentDockerFile() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDockerfile.yml"));
        PipelineParser pipelineParser  = new PipelineParser();
        Optional<PipelineModel> pipelineModel = pipelineParser.parseYaml(jenkinsFileContent);
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "dockerfile");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

}
