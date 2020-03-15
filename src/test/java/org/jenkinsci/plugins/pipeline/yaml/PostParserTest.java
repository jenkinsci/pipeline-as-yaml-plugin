package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.AgentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ScriptModel;
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
    public void postSteps() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postSteps.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        List<PostModel> postModelList = pipelineModel.getPost();
        Assert.assertEquals(10, postModelList.size());
        for(PostModel postModel : postModelList){
            List<String> postSteps = postModel.getPostSteps().getSteps();
            Assert.assertEquals(1, postSteps.size());
        }
    }
    @Test
    public void postScripts() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postScripts.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        List<PostModel> postModelList = pipelineModel.getPost();
        Assert.assertEquals(10, postModelList.size());
        for(PostModel postModel : postModelList){
            ScriptModel postScript = postModel.getPostScript();
            Assert.assertEquals(1, postScript.getScripts().size());
        }
    }
    @Test
    public void postMultiSteps() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postMultiSteps.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        List<PostModel> postModelList = pipelineModel.getPost();
        Assert.assertEquals(10, postModelList.size());
        for(PostModel postModel : postModelList){
            List<String> postSteps = postModel.getPostSteps().getSteps();
            Assert.assertEquals(2, postSteps.size());
        }
    }
    @Test
    public void postMultiScripts() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postMultiScripts.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        PipelineModel pipelineModel = pipelineParser.parse();
        List<PostModel> postModelList = pipelineModel.getPost();
        Assert.assertEquals(10, postModelList.size());
        for(PostModel postModel : postModelList){
            ScriptModel postScript = postModel.getPostScript();
            Assert.assertEquals(2, postScript.getScripts().size());
        }
    }
}
