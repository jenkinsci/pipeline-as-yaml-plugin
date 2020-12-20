package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.ChildPostModel;
import io.jenkins.plugins.pipeline.models.PostModel;

import java.util.*;

/**
 * Parser for {@link PostModel}
 */
public class PostParser extends AbstractParser implements ParserInterface<PostModel> {

    private LinkedHashMap postNode;
    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public PostParser(LinkedHashMap parentNode){
        this.yamlNodeName = PostModel.directive;
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
