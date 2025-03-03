package io.jenkins.plugins.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jenkins.plugins.pipeline.models.LibraryModel;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class LibraryParserTest {

    @Test
    void scenario1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/library/librarySingle.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<LibraryModel> library = pipelineModel.get().getLibrary();
        assertTrue(library.isPresent());
        assertEquals(1, library.get().getLibraryList().size());
        assertEquals("'library@master'", library.get().getLibraryList().get(0));
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }

    @Test
    void scenario2() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(
                new File("src/test/resources/library/libraryMulti.yml"), StandardCharsets.UTF_8);
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        assertTrue(pipelineModel.isPresent());
        Optional<LibraryModel> library = pipelineModel.get().getLibrary();
        assertTrue(library.isPresent());
        assertEquals(2, library.get().getLibraryList().size());
        assertEquals("'library@master'", library.get().getLibraryList().get(0));
        assertEquals("'library@branch'", library.get().getLibraryList().get(1));
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }
}
