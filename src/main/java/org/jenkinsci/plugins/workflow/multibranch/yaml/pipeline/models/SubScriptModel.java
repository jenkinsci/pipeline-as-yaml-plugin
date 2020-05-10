package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.Optional;


@Getter
@Setter
public class SubScriptModel extends AbstractModel implements ParsableModelInterface {

    public static String valueKey = "value";
    private String directive;
    private Optional<String> value;
    private ScriptModel scriptModel;

    public SubScriptModel(String directive, Optional<String> value, ScriptModel scriptModel) {
        this.directive = directive;
        this.value = value;
        this.scriptModel = scriptModel;
        this.scriptModel.setPrintDirective(false);
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive);
        if(value.isPresent()) {
            groovyString.append(getGetBracketsOpen())
                    .append(value.get())
                    .append(getGetBracketsClose());
        }
        groovyString.append(scriptModel.toGroovy());
        return groovyString.toString();
    }
}
