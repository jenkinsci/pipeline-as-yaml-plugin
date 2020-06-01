package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.ivy.util.StringUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class LibraryModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "library";
    private List<String> libraryList;

    public LibraryModel(List<String> libraryList) {
        this.libraryList = this.setLibrarySyntax(libraryList);
    }

    public LibraryModel(String libraryDefinition) {
        this.libraryList = this.setLibrarySyntax(Arrays.asList(libraryDefinition));
    }

    private List<String> setLibrarySyntax(List<String> libraryList) {
        ArrayList<String> convertedList = new ArrayList<>();
        libraryList.forEach(s -> {
            s = "'" + s + "'";
            convertedList.add(s);
        });
        return convertedList;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(getLibraryOpen());
        if (this.libraryList.size() == 1) {
            groovyString.append(StringUtils.join(this.libraryList.toArray(), ","));
        } else {
            groovyString.append("[");
            groovyString.append(StringUtils.join(this.libraryList.toArray(), ","));
            groovyString.append("]");
        }
        groovyString.append(getLibraryClose());

        return groovyString.toString();
    }
}
