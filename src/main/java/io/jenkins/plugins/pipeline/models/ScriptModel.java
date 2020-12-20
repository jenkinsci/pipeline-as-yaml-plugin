package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Model Class for Jenkins Declarative Pipeline Script Section
 */
@Getter
public class ScriptModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "script";
    private List scripts;
    private Boolean printDirective = true;

    /**
     * @param scripts List of scripts
     */
    public ScriptModel(List scripts) {
        this.scripts = scripts;
    }

    /**
     * @param scripts Script
     */
    public ScriptModel(String scripts) {
        this.scripts = Arrays.asList(scripts.split("\n"));
    }

    /**
     * Set Print directive
     * @param printDirective True: For printing directive/key in groovy, False: For not printing
     */
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
