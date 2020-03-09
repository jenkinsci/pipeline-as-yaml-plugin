package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PostParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void postSimple() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postSimple.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        List<PostModel> postModel = pipelineModel.getPost();
    }
}
