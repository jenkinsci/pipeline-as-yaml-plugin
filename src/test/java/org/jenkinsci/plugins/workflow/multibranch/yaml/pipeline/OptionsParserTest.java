package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.OptionsModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class OptionsParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void optionsSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/options/optionsSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<OptionsModel> optionsModel = pipelineModel.get().getOptions();
        Assert.assertTrue(optionsModel.isPresent());
        Assert.assertEquals(1, optionsModel.get().getOptionList().size());
    }

    @Test
    public void optionsMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/options/optionsMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<OptionsModel> optionsModel = pipelineModel.get().getOptions();
        Assert.assertTrue(optionsModel.isPresent());
        Assert.assertEquals(3, optionsModel.get().getOptionList().size());
    }

}
