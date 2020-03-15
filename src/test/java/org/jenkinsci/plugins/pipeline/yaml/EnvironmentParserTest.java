package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EnvironmentParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void environmentSingle() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/environment/environmentSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        EnvironmentModel environmentModel = pipelineModel.getEnvironment();
        Assert.assertEquals(1, environmentModel.getEnvironmentVariables().size());
        for(KeyValueModel keyValueModel : environmentModel.getEnvironmentVariables()){
            Assert.assertEquals("KEY1", keyValueModel.getKey());
            Assert.assertEquals("VAL1", keyValueModel.getValue());
        }
    }

    @Test
    public void environmentMulti() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/environment/environmentMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        EnvironmentModel environmentModel = pipelineModel.getEnvironment();
        Assert.assertEquals(2, environmentModel.getEnvironmentVariables().size());
        for(KeyValueModel keyValueModel : environmentModel.getEnvironmentVariables()){
            Assert.assertNotNull(keyValueModel.getKey());
            Assert.assertNotNull(keyValueModel.getValue());
        }
    }
}
