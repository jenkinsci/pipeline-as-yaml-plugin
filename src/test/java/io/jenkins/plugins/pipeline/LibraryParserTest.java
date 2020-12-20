package io.jenkins.plugins.pipeline;

import io.jenkins.plugins.pipeline.models.LibraryModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class LibraryParserTest {

    @Test
    public void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/library/librarySingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<LibraryModel> library = pipelineModel.get().getLibrary();
        Assert.assertTrue(library.isPresent());
        Assert.assertEquals(1, library.get().getLibraryList().size());
        Assert.assertEquals("'library@master'", library.get().getLibraryList().get(0));
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    public void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/library/libraryMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<LibraryModel> library = pipelineModel.get().getLibrary();
        Assert.assertTrue(library.isPresent());
        Assert.assertEquals(2, library.get().getLibraryList().size());
        Assert.assertEquals("'library@master'", library.get().getLibraryList().get(0));
        Assert.assertEquals("'library@branch'", library.get().getLibraryList().get(1));
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

}
