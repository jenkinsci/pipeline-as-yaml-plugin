package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.OptionsModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.jenkinsci.plugins.pipeline.yaml.models.TriggersModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TriggersParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void triggersSingleTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/triggers/triggersSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        TriggersModel triggersModel = pipelineModel.getTriggers();
        Assert.assertEquals(1, triggersModel.getTriggersList().size());
    }

    @Test
    public void triggersMultiTest() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/triggers/triggersMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        TriggersModel triggersModel = pipelineModel.getTriggers();
        Assert.assertEquals(2, triggersModel.getTriggersList().size());
    }

}
