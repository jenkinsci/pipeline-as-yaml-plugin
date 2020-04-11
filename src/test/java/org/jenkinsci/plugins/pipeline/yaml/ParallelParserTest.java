package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.yaml.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ParallelParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void postSteps() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/parallel/parallelScenario1.yml"));
        PipelineParser pipelineParser = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
    }

}
