package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTTools;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ChildToolModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ToolsModel;

import java.util.*;

public class ToolsParser extends AbstractParser implements ParserInterface<ToolsModel, ModelASTTools> {

    private LinkedHashMap toolsNode;

    public ToolsParser(){
        this.yamlNodeName = ToolsModel.directive;
    }

    @Override
    public Optional<ToolsModel> parse(LinkedHashMap parentNode) {
        try {
            List<ChildToolModel> childToolModels = new ArrayList<>();
            this.toolsNode = this.getChildNodeAsLinkedHashMap(parentNode);
            if( this.toolsNode == null || this.toolsNode.size() == 0)
                return Optional.empty();
            for (Object childTool : this.toolsNode.entrySet()) {
                Map.Entry childToolEntry = (Map.Entry) childTool;
                String childToolKey = (String) childToolEntry.getKey();
                String childToolValue = (String) childToolEntry.getValue();
                childToolModels.add(new ChildToolModel(childToolKey, childToolValue));
            }
            return Optional.of(new ToolsModel(childToolModels));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ToolsModel> parse(ModelASTTools modelAST) {
        return Optional.empty();
    }
}
