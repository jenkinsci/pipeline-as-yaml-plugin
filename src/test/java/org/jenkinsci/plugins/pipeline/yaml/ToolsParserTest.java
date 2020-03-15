package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ToolsParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void toolsSingle() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/tools/toolsSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        ToolsModel toolsModel = pipelineModel.getTools();
        Assert.assertEquals(1, toolsModel.getChildToolModels().size());
        for(ChildToolModel childToolModel : toolsModel.getChildToolModels()){
            Assert.assertEquals("maven", childToolModel.getToolName());
            Assert.assertEquals("maven", childToolModel.getToolType());
        }
    }

    @Test
    public void toolsMulti() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/tools/toolsMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        ToolsModel toolsModel = pipelineModel.getTools();
        Assert.assertEquals(2, toolsModel.getChildToolModels().size());
        for(ChildToolModel childToolModel : toolsModel.getChildToolModels()){
            Assert.assertEquals(childToolModel.getToolType(), childToolModel.getToolName());
        }
    }
}
