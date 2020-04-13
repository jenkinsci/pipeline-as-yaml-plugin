package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class ScriptModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "script";
    private List<String> scripts;

    public ScriptModel(List<String> scripts) {
        this.scripts = scripts;
    }

    public ScriptModel(String scripts) {
        this.scripts = Arrays.asList(scripts.split("\n"));
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer();
        groovyString
                .append(directive)
                .append(this.getDirectiveOpen());
        scripts.forEach(script -> {
            groovyString.append(script).append("\n");
        });
        groovyString.append(this.getDirectiveClose());
        return groovyString.toString();
    }
}
