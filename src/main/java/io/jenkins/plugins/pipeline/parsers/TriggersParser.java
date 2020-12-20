package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.TriggersModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link TriggersModel}
 */
public class TriggersParser extends AbstractParser implements ParserInterface<TriggersModel> {

    private List triggersNode;
    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public TriggersParser(LinkedHashMap parentNode){
        this.yamlNodeName = TriggersModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<TriggersModel> parse() {
        try {
            this.triggersNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new TriggersModel(this.triggersNode));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
