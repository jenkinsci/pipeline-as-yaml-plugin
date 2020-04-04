package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.OptionsModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class OptionsParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void optionsSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/options/optionsSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        OptionsModel optionsModel = pipelineModel.getOptions();
        Assert.assertEquals(1, optionsModel.getOptionList().size());
    }

    @Test
    public void optionsMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/options/optionsMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        OptionsModel optionsModel = pipelineModel.getOptions();
        Assert.assertEquals(3, optionsModel.getOptionList().size());
    }

}
