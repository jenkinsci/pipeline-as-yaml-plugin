package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.LibraryModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class LibraryParser extends AbstractParser implements ParserInterface<LibraryModel> {

    private LinkedHashMap parentNode;

    public LibraryParser(LinkedHashMap parentNode){
        this.yamlNodeName = LibraryModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<LibraryModel> parse() {
        try {
            Object childNode = this.parentNode.get(this.yamlNodeName);
            if( childNode instanceof String) {
                return Optional.of(new LibraryModel((String) childNode));
            }
            else if ( childNode instanceof List ) {
                return Optional.of(new LibraryModel((List) childNode));
            }
            else {
                return Optional.empty();
            }
        }
        catch (Exception p){
            return Optional.empty();
        }
    }
}
