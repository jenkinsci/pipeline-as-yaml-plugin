package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.ChildPostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class PostParser extends AbstractParser implements ParserInterface<PostModel> {

    private LinkedHashMap postNode;
    private LinkedHashMap parentNode;

    public PostParser(LinkedHashMap parentNode){
        this.yamlNodeName = PostModel.directive;
        this.yaml = new Yaml();
        this.parentNode = parentNode;
    }

    @Override
    public Optional<PostModel> parse() {
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
                    childPostModels.add(new ChildPostModel(childPostKey, Optional.empty(), new ScriptParser((LinkedHashMap) postSubNode).parse()));
                } else if (postSubNode instanceof List) {
                    childPostModels.add(new ChildPostModel(childPostKey, new StepsParser((List<String>) postSubNode).parse(), Optional.empty()));
                }
            }
            return Optional.of(new PostModel(childPostModels));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
