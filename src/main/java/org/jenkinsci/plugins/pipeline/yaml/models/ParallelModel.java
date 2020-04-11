package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class ParallelModel extends AbstractModel implements ParsableModelInterface {

    private Boolean failFast = false;
    private List<StageModel> stageModelList;

    public ParallelModel(List<StageModel> stageModelList) {
        this.stageModelList = stageModelList;
    }
}
