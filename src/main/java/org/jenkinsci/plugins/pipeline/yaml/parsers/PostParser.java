package org.jenkinsci.plugins.pipeline.yaml.parsers;

import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParserInterface;
import org.jenkinsci.plugins.pipeline.yaml.models.AbstractModel;
import org.jenkinsci.plugins.pipeline.yaml.models.PostModel;
import org.jenkinsci.plugins.pipeline.yaml.models.ScriptModel;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PostParser extends AbstractParser implements ParserInterface<List<PostModel>> {

    private LinkedHashMap postNode;
    private LinkedHashMap pipelineNode;

    public PostParser(LinkedHashMap pipelineNode){
        this.yamlNodeName = "post";
        this.nodeRequired = false;
        this.yaml = new Yaml();
        this.pipelineNode = pipelineNode;
    }

    @Override
    public List<PostModel> parse() {
        List<PostModel> post = new ArrayList<>();
        this.postNode = this.getChildNode(pipelineNode);
        List<String> keyList = this.getKeyList(this.postNode);
        for(String key : keyList) {
            Object postSubNode = this.postNode.get(key);
            if( postSubNode instanceof LinkedHashMap) {
                post.add(new PostModel(key, null, new ScriptParser((LinkedHashMap) postSubNode).parse()));
            }
            else if( postSubNode instanceof List){
                post.add(new PostModel(key, new StepsParser((List<String>) postSubNode).parse(), null));
            }
        }
        return post;
    }
}
