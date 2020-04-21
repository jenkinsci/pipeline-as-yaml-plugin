package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class WhenModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "when";
    private Optional<WhenConditionModel> whenConditionModel = Optional.empty();
    private List<String> whenRuleList = new ArrayList<>();
    public static final String beforeAgentKey = "beforeAgent";
    private Optional<Boolean> beforeAgent;

    public WhenModel(List<String> whenRuleList) {
        this.whenRuleList = whenRuleList;
    }

    public WhenModel(Optional<WhenConditionModel> whenConditionModel) {
        this.whenConditionModel = whenConditionModel;
    }



    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString.append(directive).append(getDirectiveOpen());
        groovyString.append(optionalBooleanToGroovy(this.beforeAgent, beforeAgentKey)).append("\n");
        whenRuleList.forEach(rule -> {
            groovyString.append(rule).append("\n");
        });
        groovyString.append(whenConditionModel.map(WhenConditionModel::toGroovy).orElse(""));
        groovyString.append(getDirectiveClose());
        return groovyString.toString();
    }
}
