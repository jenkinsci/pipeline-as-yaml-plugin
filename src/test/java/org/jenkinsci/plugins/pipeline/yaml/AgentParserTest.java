package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class AgentParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void agentAnyTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentAny.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "any");
    }

    @Test
    public void agentNoneTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNone.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "none");
    }

    @Test
    public void agentLabel() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentLabel.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "node");
        Assert.assertEquals(1, agentModel.getAgentParameter().size());
        Assert.assertEquals(agentModel.getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentNode() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentNode.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "node");
        Assert.assertEquals(1, agentModel.getAgentParameter().size());
        Assert.assertEquals(agentModel.getAgentParameter().get(0).getKey(), "label");
        Assert.assertEquals(agentModel.getAgentParameter().get(0).getValue(), "label");
    }

    @Test
    public void agentDocker() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDocker.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "docker");
        Assert.assertEquals(5, agentModel.getAgentParameter().size());
    }

    @Test
    public void agentDockerFile() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/agent/agentDockerfile.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        AgentModel agentModel = pipelineModel.getAgent();
        Assert.assertEquals(agentModel.getAgentType(), "dockerfile");
        Assert.assertEquals(5, agentModel.getAgentParameter().size());
    }
}
