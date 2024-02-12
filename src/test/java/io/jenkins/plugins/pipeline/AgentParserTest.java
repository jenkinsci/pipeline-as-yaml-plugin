package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.AgentModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AgentParserTest {

    @Before
    public void setup() {}

    @Test
    public void agentAnyTest() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentAny.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "any");
    }

    @Test
    public void agentAnyShortTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentAnyShort.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "any");
    }

    @Test
    public void agentNoneTest() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentNone.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "none");
    }

    @Test
    public void agentLabel() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentLabel.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
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
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentNode.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
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
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentDocker.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "docker");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    public void agentDockerFile() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentDockerfile.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals(agentModel.get().getAgentType(), "dockerfile");
        Assert.assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    public void agentKubernetes() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentKubernetes.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        Assert.assertTrue(agentModel.isPresent());
        Assert.assertEquals("kubernetes", agentModel.get().getAgentType());
        Assert.assertEquals(1, agentModel.get().getAgentParameter().size());
        Assert.assertEquals("yaml", agentModel.get().getAgentParameter().get(0).getKey());
        Assert.assertEquals(
                "pipeline {\n"
                        + "  agent {\n"
                        + "    kubernetes {\n"
                        + "      yaml \"\"\"\n"
                        + "      apiVersion: v1\n"
                        + "      kind: Pod\n"
                        + "      spec:\n"
                        + "        imagePullSecrets:\n"
                        + "        - name: my-creds\n"
                        + "        containers:\n"
                        + "        - name: ubuntu\n"
                        + "          image: myimage:1.1\n"
                        + "          command: ['sleep', 'infinity']\n"
                        + "          tty: true\n"
                        + "          imagePullPolicy: Always\n"
                        + "      \"\"\"\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n",
                pipelineModel.get().toPrettyGroovy());
    }
}
