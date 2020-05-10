package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import javax.jws.Oneway;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class ScriptModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "script";
    private List scripts;
    private Boolean printDirective = true;

    public ScriptModel(List scripts) {
        this.scripts = scripts;
    }

    public ScriptModel(String scripts) {
        this.scripts = Arrays.asList(scripts.split("\n"));
    }

    public void setPrintDirective(Boolean printDirective) {
        this.printDirective = printDirective;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(printDirective ? directive:"")
                .append(this.getDirectiveOpen());
        for(Object script : this.scripts) {
            if( script instanceof String)
                groovyString.append(script).append("\n");
            else {
                Optional<SubScriptModel> subScriptModel = (Optional<SubScriptModel>) script;
                groovyString.append(subScriptModel.map(SubScriptModel::toGroovy).orElse(""));
            }
        }
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }

}
