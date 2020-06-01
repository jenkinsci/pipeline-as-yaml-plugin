package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPostBuild;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ChildPostModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PostModel;

import java.util.*;

public class PostParser extends AbstractParser implements ParserInterface<PostModel> {

    private LinkedHashMap postNode;

    public PostParser(){
        this.yamlNodeName = PostModel.directive;
    }

    @Override
    public Optional<PostModel> parse(LinkedHashMap parentNode) {
        try {
            List<ChildPostModel> childPostModels = new ArrayList<>();
            this.postNode = this.getChildNodeAsLinkedHashMap(parentNode);
            if (this.postNode == null || this.postNode.size() == 0)  {
                return Optional.empty();
            }
            for (Object childPost : this.postNode.entrySet()) {
                Map.Entry childPostNode = (Map.Entry) childPost;
                String childPostKey = (String) childPostNode.getKey();
                Object postSubNode = this.postNode.get(childPostKey);
                if (postSubNode instanceof LinkedHashMap) {
                    childPostModels.add(new ChildPostModel(childPostKey, Optional.empty(), new ScriptParser().parse((LinkedHashMap) postSubNode)));
                } else if (postSubNode instanceof List) {
                    childPostModels.add(new ChildPostModel(childPostKey, new StepsParser().parse((List<String>) postSubNode), Optional.empty()));
                }
            }
            return Optional.of(new PostModel(childPostModels));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PostModel> parse(ModelASTPipelineDef modelASTPipelineDef) {
        return Optional.empty();
    }

}
