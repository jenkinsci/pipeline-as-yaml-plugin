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
    public static String beforeAgentKey = "beforeAgent";
    public static String beforeOptionsKey = "beforeOptions";
    public static String beforeInputKey = "beforeInput";
    private Optional<Boolean> beforeAgent;
    private Optional<Boolean> beforeOptions;
    private Optional<Boolean> beforeInput;

    public WhenModel(List<String> whenRuleList) {
        this.whenRuleList = whenRuleList;
    }

    public WhenModel(Optional<WhenConditionModel> whenConditionModel) {
        this.whenConditionModel = whenConditionModel;
    }



    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString.append(optionalBooleanToGroovy(this.beforeAgent, beforeAgentKey))
                .append(optionalBooleanToGroovy(this.beforeOptions,beforeOptionsKey))
                .append(optionalBooleanToGroovy(this.beforeInput,beforeInputKey));
        whenRuleList.forEach(rule -> {
            groovyString.append(rule).append("\n");
        });
        groovyString.append(whenConditionModel.map(WhenConditionModel::toGroovy).orElse(""));
        return groovyString.toString();
    }
}
