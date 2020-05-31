package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.modelAst;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.AgentModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AgentParserTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Test
    public void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/agent/Jenkinsfile1"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<AgentModel> agent = pipelineModel.getAgent();
        Assert.assertTrue(agent.isPresent());
        AgentModel agentModel = agent.get();
        Assert.assertEquals("node", agentModel.getAgentType());
        Assert.assertEquals(1, agentModel.getAgentParameter().size());
        Assert.assertEquals("label", agentModel.getAgentParameter().get(0).getKey());
        Assert.assertEquals("master", agentModel.getAgentParameter().get(0).getValue());
    }

    @Test
    public void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/agent/Jenkinsfile2"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<AgentModel> agent = pipelineModel.getAgent();
        Assert.assertTrue(agent.isPresent());
        AgentModel agentModel = agent.get();
        Assert.assertEquals("node", agentModel.getAgentType());
        Assert.assertEquals(1, agentModel.getAgentParameter().size());
        Assert.assertEquals("label", agentModel.getAgentParameter().get(0).getKey());
        Assert.assertEquals("master", agentModel.getAgentParameter().get(0).getValue());
    }

    @Test
    public void scenario3() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/agent/Jenkinsfile3"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<AgentModel> agent = pipelineModel.getAgent();
        Assert.assertTrue(agent.isPresent());
        AgentModel agentModel = agent.get();
        Assert.assertEquals("node", agentModel.getAgentType());
        Assert.assertEquals(2, agentModel.getAgentParameter().size());
        Assert.assertEquals("label", agentModel.getAgentParameter().get(0).getKey());
        Assert.assertEquals("master", agentModel.getAgentParameter().get(0).getValue());
        Assert.assertEquals("customWorkspace", agentModel.getAgentParameter().get(1).getKey());
        Assert.assertEquals("/tmp/", agentModel.getAgentParameter().get(1).getValue());
    }

    @Test
    public void scenario4() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/agent/Jenkinsfile4"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<AgentModel> agent = pipelineModel.getAgent();
        Assert.assertTrue(agent.isPresent());
        AgentModel agentModel = agent.get();
        Assert.assertEquals("docker", agentModel.getAgentType());
        Assert.assertEquals(2, agentModel.getAgentParameter().size());
        Assert.assertEquals("image", agentModel.getAgentParameter().get(0).getKey());
        Assert.assertEquals("myregistry.com/node", agentModel.getAgentParameter().get(0).getValue());
        Assert.assertEquals("label", agentModel.getAgentParameter().get(1).getKey());
        Assert.assertEquals("my-defined-label", agentModel.getAgentParameter().get(1).getValue());
    }

    @Test
    public void scenario5() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/agent/Jenkinsfile5"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<AgentModel> agent = pipelineModel.getAgent();
        Assert.assertTrue(agent.isPresent());
        AgentModel agentModel = agent.get();
        Assert.assertEquals("dockerfile", agentModel.getAgentType());
        Assert.assertEquals(3, agentModel.getAgentParameter().size());
        Assert.assertEquals("filename", agentModel.getAgentParameter().get(0).getKey());
        Assert.assertEquals("Dockerfile.build", agentModel.getAgentParameter().get(0).getValue());
        Assert.assertEquals("dir", agentModel.getAgentParameter().get(1).getKey());
        Assert.assertEquals("build", agentModel.getAgentParameter().get(1).getValue());
        Assert.assertEquals("label", agentModel.getAgentParameter().get(2).getKey());
        Assert.assertEquals("my-defined-label", agentModel.getAgentParameter().get(2).getValue());
    }
}
