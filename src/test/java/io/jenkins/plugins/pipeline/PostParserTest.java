package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.*;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class PostParserTest {

    @Test
    void postSteps() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/post/postSteps.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        assertTrue(postModel.isPresent());
        assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            List<String> postSteps = childPostModel.getPostSteps().get().getSteps();
            assertEquals(1, postSteps.size());
        }
    }

    @Test
    void postScripts() throws IOException {
        String jenkinsFileContent =
                FileUtils.readFileToString(new File("src/test/resources/post/postScripts.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        assertTrue(postModel.isPresent());
        assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<ScriptModel> postScript = childPostModel.getPostScript();
            assertTrue(postScript.isPresent());
            assertEquals(1, postScript.get().getScripts().size());
        }
    }

    @Test
    void postMultiSteps() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/post/postMultiSteps.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        assertTrue(postModel.isPresent());
        assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<StepsModel> postSteps = childPostModel.getPostSteps();
            assertTrue(postSteps.isPresent());
            List<String> stepsList = postSteps.get().getSteps();
            assertEquals(2, stepsList.size());
        }
    }

    @Test
    void postMultiScripts() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/post/postMultiScripts.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<PostModel> postModel = pipelineModel.get().getPost();
        assertTrue(postModel.isPresent());
        assertEquals(10, postModel.get().getChildPostModels().size());
        for (ChildPostModel childPostModel : postModel.get().getChildPostModels()) {
            Optional<ScriptModel> postScript = childPostModel.getPostScript();
            assertTrue(postScript.isPresent());
            assertEquals(2, postScript.get().getScripts().size());
        }
    }
}
