package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class StagesModel extends AbstractModel implements ParsableModelInterface {

    private List<StageModel> stageModelList;

    public StagesModel(List<StageModel> stageModelList) {
        this.stageModelList = stageModelList;
    }
}
