package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Model Class for {@link ScriptModel} inner script definitions
 */
@Getter
@Setter
public class SubScriptModel extends AbstractModel implements ParsableModelInterface {

    public static String valueKey = "value";
    private String directive;
    private Optional<String> value;
    private ScriptModel scriptModel;

    /**
     * @param directive Name of the directive
     * @param value Value of the directive
     * @param scriptModel {@link ScriptModel}
     */
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
