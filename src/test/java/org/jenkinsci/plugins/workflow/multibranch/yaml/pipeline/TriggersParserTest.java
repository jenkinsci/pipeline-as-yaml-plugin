package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.TriggersModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TriggersParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void triggersSingleTest() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/triggers/triggersSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        Assert.assertTrue(triggersModel.isPresent());
        Assert.assertEquals(1, triggersModel.get().getTriggersList().size());
    }

    @Test
    public void triggersMultiTest() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/triggers/triggersMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<TriggersModel> triggersModel = pipelineModel.get().getTriggers();
        Assert.assertTrue(triggersModel.isPresent());
        Assert.assertEquals(2, triggersModel.get().getTriggersList().size());
    }

}
