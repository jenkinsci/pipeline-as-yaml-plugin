package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.LibraryModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link LibraryModel}
 */
public class LibraryParser extends AbstractParser implements ParserInterface<LibraryModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public LibraryParser(LinkedHashMap parentNode){
        this.yamlNodeName = LibraryModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<LibraryModel> parse() {
        try {
            Object childNode = this.getChildNodeAsObject(parentNode);
            if( childNode instanceof String) {
                return Optional.of(new LibraryModel((String) childNode));
            }
            else if ( childNode instanceof List ) {
                return Optional.of(new LibraryModel((List) childNode));
            }
            else {
                throw new PipelineAsYamlUnknownTypeException(childNode.getClass().toString());
            }
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
