package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.EnvironmentModel;
import org.jenkinsci.plugins.pipeline.yaml.models.OptionsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;

public class OptionsParser extends AbstractParser implements ParserInterface<OptionsModel> {

    private List optionsNode;
    private LinkedHashMap pipelineNode;

    public OptionsParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "options";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public OptionsModel parse() {
        this.optionsNode = (List) this.getChildNode(pipelineNode);
        return new OptionsModel(this.optionsNode);
    }
}
