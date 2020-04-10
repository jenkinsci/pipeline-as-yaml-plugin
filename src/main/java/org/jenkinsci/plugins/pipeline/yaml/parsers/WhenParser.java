package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ChildToolModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ToolsModel;
import org.jenkinsci.plugins.pipeline.yaml.models.WhenModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WhenParser extends AbstractParser implements ParserInterface<WhenModel> {

    private LinkedHashMap whenNode;
    private LinkedHashMap pipelineNode;

    public WhenParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "tools";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public WhenModel parse() {
        return null;
    }
}
