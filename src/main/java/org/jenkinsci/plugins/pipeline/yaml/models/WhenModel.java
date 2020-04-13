package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class WhenModel extends AbstractModel implements ParsableModelInterface {

    public static String directive = "when";
    private Optional<WhenConditionModel> whenConditionModel = Optional.empty();
    private List<String> whenRuleList = new ArrayList<>();

    public WhenModel(List<String> whenRuleList) {
        this.whenRuleList = whenRuleList;
    }

    public WhenModel(Optional<WhenConditionModel> whenConditionModel) {
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
