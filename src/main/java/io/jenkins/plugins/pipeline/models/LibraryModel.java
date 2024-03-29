package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.ivy.util.StringUtils;

/**
 * Model Class for Shared Library Definitions
 */
@Getter
public class LibraryModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "library";
    private List<String> libraryList;

    /**
     * @param libraryList List of library definitions
     */
    public LibraryModel(List<String> libraryList) {
        this.libraryList = this.setLibrarySyntax(libraryList);
    }

    /**
     * @param libraryDefinition Library definition
     */
    public LibraryModel(String libraryDefinition) {
        this.libraryList = this.setLibrarySyntax(Arrays.asList(libraryDefinition));
    }

    /**
     * Convert library definitions list to Jenkins Declarative Pipeline syntax
     *
     * @param libraryList List of library definitions
     * @return List of converted definitions
     */
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
        StringBuffer groovyString = new StringBuffer().append(getLibraryOpen());
        if (this.libraryList.size() == 1) {
            groovyString.append(StringUtils.joinArray(this.libraryList.toArray(new String[libraryList.size()]), ","));
        } else {
            groovyString.append("[");
            groovyString.append(StringUtils.joinArray(this.libraryList.toArray(new String[libraryList.size()]), ","));
            groovyString.append("]");
        }
        groovyString.append(getLibraryClose());

        return groovyString.toString();
    }
}
