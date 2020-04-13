package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class ParallelModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "parallel";
    private List<StageModel> stageModelList;

    public ParallelModel(List<StageModel> stageModelList) {
        this.stageModelList = stageModelList;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive)
                .append(getDirectiveOpen());
        stageModelList.forEach(stageModel -> groovyString.append(stageModel.toGroovy()));
        return groovyString.append(getDirectiveClose()).toString();
    }
}
