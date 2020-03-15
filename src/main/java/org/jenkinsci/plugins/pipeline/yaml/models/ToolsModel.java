package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class ToolsModel extends AbstractModel implements ParsableModelInterface {

    private List<ChildToolModel> childToolModels;

    public ToolsModel(List<ChildToolModel> childToolModels) {
        this.childToolModels = childToolModels;
    }
}
