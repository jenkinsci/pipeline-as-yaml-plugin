package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.OptionsModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ParametersModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ParametersParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void optionsSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parameters/parametersSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        ParametersModel parametersModel = pipelineModel.getParameters();
        Assert.assertEquals(1, parametersModel.getParametersList().size());
    }

    @Test
    public void optionsMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parameters/parametersMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        ParametersModel parametersModel = pipelineModel.getParameters();
        Assert.assertEquals(2, parametersModel.getParametersList().size());
    }

}
