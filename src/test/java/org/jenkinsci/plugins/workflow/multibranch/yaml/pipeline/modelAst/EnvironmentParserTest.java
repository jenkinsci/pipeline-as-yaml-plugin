package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.modelAst;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.EnvironmentModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class EnvironmentParserTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Test
    public void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/environment/Jenkinsfile1"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<EnvironmentModel> environment = pipelineModel.getEnvironment();
        Assert.assertTrue(environment.isPresent());
        EnvironmentModel environmentModel = environment.get();
        Assert.assertEquals(1, environmentModel.getEnvironmentVariables().size());
        Assert.assertEquals("KEY1", environmentModel.getEnvironmentVariables().get(0).getKey());
        Assert.assertEquals("VAL1", environmentModel.getEnvironmentVariables().get(0).getValue());
    }

    @Test
    public void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/modelAst/environment/Jenkinsfile2"));
        ModelASTPipelineDef modelASTPipelineDef = Converter.scriptToPipelineDef(jenkinsFileContent);
        PipelineParser pipelineParser = new PipelineParser();
        Optional<PipelineModel> parse = pipelineParser.parse(modelASTPipelineDef);
        Assert.assertTrue(parse.isPresent());
        PipelineModel pipelineModel = parse.get();
        Optional<EnvironmentModel> environment = pipelineModel.getEnvironment();
        Assert.assertTrue(environment.isPresent());
        EnvironmentModel environmentModel = environment.get();
        Assert.assertEquals(1, environmentModel.getEnvironmentVariables().size());
        Assert.assertEquals("KEY1", environmentModel.getEnvironmentVariables().get(0).getKey());
        Assert.assertEquals("credentials('test-credential')", environmentModel.getEnvironmentVariables().get(0).getValue());
    }
}

