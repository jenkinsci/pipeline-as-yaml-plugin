package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class WhenConditionModel extends AbstractModel implements ParsableModelInterface {

    private String conditionName;
    private List<String> whenRuleList = new ArrayList<>();
    private Optional<WhenConditionModel> whenConditionModel = Optional.empty();

    public WhenConditionModel(String conditionName, List<String> whenRuleList) {
        this.conditionName = conditionName;
        this.whenRuleList = whenRuleList;
    }

    public WhenConditionModel(String conditionName, Optional<WhenConditionModel> whenConditionModel) {
        this.conditionName = conditionName;
        this.whenConditionModel = whenConditionModel;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        whenRuleList.forEach(rule -> {
            groovyString.append(rule).append("\n");
        });
        groovyString.append(whenConditionModel.map(WhenConditionModel::toGroovy).orElse(""));
        return groovyString.toString();
    }
}
