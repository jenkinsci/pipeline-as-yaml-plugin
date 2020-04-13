package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.jenkinsci.plugins.pipeline.yaml.models.TriggersModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PipelineParserTest {


    @Before
    public void setup() {
    }

    @Test
    public void pipeline1() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/pipeline/pipeline1.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        System.out.println(pipelineModel.get().toGroovy());
        System.out.println(pipelineModel.get().toPrettyGroovy());
    }
}
