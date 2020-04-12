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


    private Optional<WhenConditionModel> whenConditionModel = Optional.empty();
    private List<String> whenRuleList = new ArrayList<>();
    private Boolean beforeOptions = false;
    private Boolean beforeInput = false;
    private Boolean beforeAgent = false;

    public WhenModel(List<String> whenRuleList) {
        this.whenRuleList = whenRuleList;
    }

    public WhenModel(Optional<WhenConditionModel> whenConditionModel) {
        this.whenConditionModel = whenConditionModel;
    }
}
