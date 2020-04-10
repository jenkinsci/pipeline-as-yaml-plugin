package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.List;


@Getter
@Setter
public class WhenModel extends AbstractModel implements ParsableModelInterface {


    private WhenConditionModel whenConditionModel;
    private List<WhenRuleModel> whenRuleModelList;
    private Boolean beforeOptions = false;
    private Boolean beforeInput = false;
    private Boolean beforeAgent = false;

    public WhenModel(WhenConditionModel whenConditionModel) {
        this.whenConditionModel = whenConditionModel;
    }

    public WhenModel(List<WhenRuleModel> whenRuleModelList) {
        this.whenRuleModelList = whenRuleModelList;
    }

}
