package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ChildPostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ChildToolModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ToolsModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ToolsParser extends AbstractParser implements ParserInterface<ToolsModel> {

    private LinkedHashMap toolsNode;
    private LinkedHashMap pipelineNode;

    public ToolsParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "tools";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public ToolsModel parse() {
        List<ChildToolModel> childToolModels = new ArrayList<>();
        this.toolsNode = this.getChildNode(pipelineNode);
        for(Object childTool : this.toolsNode.entrySet()){
            Map.Entry childToolEntry = (Map.Entry) childTool;
            String childToolKey = (String) childToolEntry.getKey();
            String childToolValue = (String) childToolEntry.getValue();
            childToolModels.add(new ChildToolModel(childToolKey, childToolValue));
        }
        return new ToolsModel(childToolModels);
    }
}
