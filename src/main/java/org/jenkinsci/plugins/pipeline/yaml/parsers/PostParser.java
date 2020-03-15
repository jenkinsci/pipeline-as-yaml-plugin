package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AbstractModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ChildPostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ScriptModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostParser extends AbstractParser implements ParserInterface<PostModel> {

    private LinkedHashMap postNode;
    private LinkedHashMap pipelineNode;

    public PostParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "post";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public PostModel parse() {
        List<ChildPostModel> childPostModels = new ArrayList<>();
        this.postNode = this.getChildNode(pipelineNode);
        if( this.postNode == null) {
            return new PostModel(new ArrayList<>());
        }
        for(Object childPost : this.postNode.entrySet()){
            Map.Entry childPostNode = (Map.Entry) childPost;
            String childPostKey = (String) childPostNode.getKey();
            Object postSubNode = this.postNode.get(childPostKey);
            if( postSubNode instanceof LinkedHashMap) {
                childPostModels.add(new ChildPostModel(childPostKey, null, new ScriptParser((LinkedHashMap) postSubNode).parse()));
            }
            else if( postSubNode instanceof List){
                childPostModels.add(new ChildPostModel(childPostKey, new StepsParser((List<String>) postSubNode).parse(), null));
            }
        }
        return new PostModel(childPostModels);
    }
}
