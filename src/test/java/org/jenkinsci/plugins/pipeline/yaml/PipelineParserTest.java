package org.jenkinsci.plugins.pipeline.yaml;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.pipeline.yaml.models.PipelineModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;

public class PipelineParserTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    private String jenkinsFileContent;


    @Before
    public void setup() throws IOException {
        this.jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/Jenkinsfile"));
    }

    @Test
    public void parseJenkinsFile() {
        PipelineParser.parse(this.jenkinsFileContent);
    }
}
