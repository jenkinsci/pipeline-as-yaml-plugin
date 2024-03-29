package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.OptionsModel;
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

public class OptionsParserTest {

    @Before
    public void setup() {}

    @Test
    public void optionsSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/options/optionsSingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<OptionsModel> optionsModel = pipelineModel.get().getOptions();
        Assert.assertTrue(optionsModel.isPresent());
        Assert.assertEquals(1, optionsModel.get().getOptionList().size());
    }

    @Test
    public void optionsMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/options/optionsMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<OptionsModel> optionsModel = pipelineModel.get().getOptions();
        Assert.assertTrue(optionsModel.isPresent());
        Assert.assertEquals(3, optionsModel.get().getOptionList().size());
    }
}
