package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.ParallelModel;
import io.jenkins.plugins.pipeline.models.StageModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link ParallelModel}
 */
public class ParallelParser extends AbstractParser implements ParserInterface<ParallelModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public ParallelParser(LinkedHashMap parentNode){
        this.yamlNodeName = ParallelModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ParallelModel> parse() {
        try {
            List<StageModel> stageModelList = new ArrayList<>();
            Object parallelObject = this.getChildNodeAsObject(parentNode);
            if (parallelObject instanceof List) {
                for (LinkedHashMap childStage : (List<LinkedHashMap>) parallelObject) {
                    Optional<StageModel> stageModel = new StageParser(childStage).parse();
                    stageModel.ifPresent(stageModelList::add);
                }
                return Optional.of(new ParallelModel(stageModelList));
            }
            else {
                throw new PipelineAsYamlUnknownTypeException(parallelObject.getClass().toString());
            }
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
