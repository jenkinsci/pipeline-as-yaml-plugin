package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Parallel Stage Section
 */
@Getter
public class ParallelModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "parallel";
    private List<StageModel> stageModelList;

    /**
     * @param stageModelList List of {@link StageModel}
     */
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
