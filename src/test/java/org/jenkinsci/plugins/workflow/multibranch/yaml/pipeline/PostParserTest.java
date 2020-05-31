package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.*;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PostParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void postSteps() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postSteps.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        Assert.assertTrue(postModel.isPresent());
        Assert.assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            List<String> postSteps = childPostModel.getPostSteps().get().getSteps();
            Assert.assertEquals(1, postSteps.size());
        }
    }

    @Test
    public void postScripts() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postScripts.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        Assert.assertTrue(postModel.isPresent());
        Assert.assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<ScriptModel> postScript = childPostModel.getPostScript();
            Assert.assertTrue(postScript.isPresent());
            Assert.assertEquals(1, postScript.get().getScripts().size());
        }
    }

    @Test
    public void postMultiSteps() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postMultiSteps.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        Assert.assertTrue(postModel.isPresent());
        Assert.assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<StepsModel> postSteps = childPostModel.getPostSteps();
            Assert.assertTrue(postSteps.isPresent());
            List<String> stepsList = postSteps.get().getSteps();
            Assert.assertEquals(2, stepsList.size());
        }
    }

    @Test
    public void postMultiScripts() throws IOException, PipelineAsYamlNodeNotFoundException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/post/postMultiScripts.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        Assert.assertTrue(postModel.isPresent());
        Assert.assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<ScriptModel> postScript = childPostModel.getPostScript();
            Assert.assertTrue(postScript.isPresent());
            Assert.assertEquals(2, postScript.get().getScripts().size());
        }
    }
}
