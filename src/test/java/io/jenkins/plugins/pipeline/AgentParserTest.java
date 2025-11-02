package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.AgentModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class AgentParserTest {

    @Test
    void agentAnyTest() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentAny.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("any", agentModel.get().getAgentType());
    }

    @Test
    void agentAnyShortTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentAnyShort.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("any", agentModel.get().getAgentType());
    }

    @Test
    void agentNoneTest() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentNone.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("none", agentModel.get().getAgentType());
    }

    @Test
    void agentLabel() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentLabel.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("node", agentModel.get().getAgentType());
        assertEquals(1, agentModel.get().getAgentParameter().size());
        assertEquals("label", agentModel.get().getAgentParameter().get(0).getKey());
        assertEquals("label", agentModel.get().getAgentParameter().get(0).getValue());
    }

    @Test
    void agentNode() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/agent/agentNode.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("node", agentModel.get().getAgentType());
        assertEquals(1, agentModel.get().getAgentParameter().size());
        assertEquals("label", agentModel.get().getAgentParameter().get(0).getKey());
        assertEquals("label", agentModel.get().getAgentParameter().get(0).getValue());
    }

    @Test
    void agentDocker() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentDocker.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("docker", agentModel.get().getAgentType());
        assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    void agentDockerFile() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentDockerfile.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("dockerfile", agentModel.get().getAgentType());
        assertEquals(5, agentModel.get().getAgentParameter().size());
    }

    @Test
    void agentKubernetes() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/agent/agentKubernetes.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<AgentModel> agentModel = pipelineModel.get().getAgent();
        assertTrue(agentModel.isPresent());
        assertEquals("kubernetes", agentModel.get().getAgentType());
        assertEquals(1, agentModel.get().getAgentParameter().size());
        assertEquals("yaml", agentModel.get().getAgentParameter().get(0).getKey());
        assertEquals("""
                        pipeline {
                          agent {
                            kubernetes {
                              yaml ""\"
                              apiVersion: v1
                              kind: Pod
                              spec:
                                imagePullSecrets:
                                - name: my-creds
                                containers:
                                - name: ubuntu
                                  image: myimage:1.1
                                  command: ['sleep', 'infinity']
                                  tty: true
                                  imagePullPolicy: Always
                              ""\"
                            }
                          }
                        }
                        """, pipelineModel.get().toPrettyGroovy());
    }
}
