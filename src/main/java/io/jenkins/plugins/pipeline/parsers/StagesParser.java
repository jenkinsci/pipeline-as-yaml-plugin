package io.jenkins.plugins.pipeline.parsers;

import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugins.pipeline.interfaces.ParserInterface;
import io.jenkins.plugins.pipeline.models.StageModel;
import io.jenkins.plugins.pipeline.models.StagesModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link StagesModel}
 */
public class StagesParser extends AbstractParser implements ParserInterface<StagesModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public StagesParser(LinkedHashMap parentNode) {
        this.yamlNodeName = StagesModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StagesModel> parse() {
        try {
            List<StageModel> stageModelList = new ArrayList<>();
            Object stagesObject = this.getChildNodeAsObject(parentNode);
            if (stagesObject == null) {
                return Optional.empty();
            }
            if (stagesObject instanceof List) {
                for (LinkedHashMap childStage : (List<LinkedHashMap>) stagesObject) {
                    Optional<StageModel> stageModel = new StageParser(childStage).parse();
                    stageModel.ifPresent(stageModelList::add);
                }
                return Optional.of(new StagesModel(stageModelList));
            } else {
                throw new PipelineAsYamlUnknownTypeException(stagesObject.getClass().toString());
            }
        } catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
