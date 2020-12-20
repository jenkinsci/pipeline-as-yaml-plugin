package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Model Class for {@link WhenModel} Conditions
 */
@Getter
@Setter
public class WhenConditionModel extends AbstractModel implements ParsableModelInterface {

    private String conditionName;
    private List<String> whenRuleList = new ArrayList<>();
    private Optional<WhenConditionModel> whenConditionModel = Optional.empty();

    /**
     * @param conditionName Name of the condition
     * @param whenRuleList List of rules
     */
    public WhenConditionModel(String conditionName, List<String> whenRuleList) {
        this.conditionName = conditionName;
        this.whenRuleList = whenRuleList;
    }

    /**
     * @param conditionName Name of the condition
     * @param whenConditionModel {@link WhenConditionModel}
     */
    public WhenConditionModel(String conditionName, Optional<WhenConditionModel> whenConditionModel) {
        this.conditionName = conditionName;
        this.whenConditionModel = whenConditionModel;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString.append(conditionName).append(getDirectiveOpen());
        groovyString.append(whenConditionModel.map(WhenConditionModel::toGroovy).orElse(""));
        whenRuleList.forEach(rule -> {
            groovyString.append(rule).append("\n");
        });
        groovyString.append(getDirectiveClose());
        return groovyString.toString();
    }
}
