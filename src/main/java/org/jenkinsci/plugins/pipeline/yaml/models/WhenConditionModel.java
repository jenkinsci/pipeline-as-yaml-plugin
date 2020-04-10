package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class WhenConditionModel extends AbstractModel implements ParsableModelInterface {

    private String conditionName;
    private List<WhenRuleModel> whenRuleModelList;
    private WhenConditionModel whenConditionModel;

    public WhenConditionModel(String conditionName) {
        this.conditionName = conditionName;
        this.whenRuleModelList = new ArrayList<>();
    }

    public WhenConditionModel(String conditionName, List<WhenRuleModel> whenRuleModelList){
        this.conditionName = conditionName;
        this.whenRuleModelList = whenRuleModelList;
    }

    public WhenConditionModel(String conditionName, WhenConditionModel whenConditionModel){
        this.conditionName = conditionName;
        this.whenConditionModel = whenConditionModel;
    }


}
