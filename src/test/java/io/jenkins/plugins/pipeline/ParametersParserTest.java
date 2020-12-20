package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.ParametersModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ParametersParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void parametersSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parameters/parametersSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<ParametersModel> parametersModel = pipelineModel.get().getParameters();
        Assert.assertTrue(parametersModel.isPresent());
        Assert.assertEquals(1, parametersModel.get().getParametersList().size());
    }

    @Test
    public void parametersMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parameters/parametersMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<ParametersModel> parametersModel = pipelineModel.get().getParameters();
        Assert.assertTrue(parametersModel.isPresent());
        Assert.assertEquals(2, parametersModel.get().getParametersList().size());
    }

}
