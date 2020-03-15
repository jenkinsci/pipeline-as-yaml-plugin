package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class PostModel extends AbstractModel implements ParsableModelInterface {

    private List<ChildPostModel> childPostModels;

    public PostModel(List<ChildPostModel> childPostModels) {
        this.childPostModels = childPostModels;
    }
}
