package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlNodeNotFoundException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.exceptions.PipelineAsYamlRuntimeException;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces.ParserInterface;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;

import java.util.LinkedHashMap;
import java.util.Optional;

public class PipelineParser extends AbstractParser implements ParserInterface<PipelineModel, ModelASTPipelineDef> {

    private String jenkinsFileAsYamlContent;
    private PipelineModel pipelineModel;

    public PipelineParser(String jenkinsFileAsYamlContent){
        this.jenkinsFileAsYamlContent = jenkinsFileAsYamlContent;
        this.yamlNodeName = PipelineModel.directive;
    }

    public Optional<PipelineModel> parse() throws PipelineAsYamlNodeNotFoundException {
        LinkedHashMap jenkinsFileHashMap = yaml.load(this.jenkinsFileAsYamlContent);
        LinkedHashMap pipelineNode = this.getChildNodeAsLinkedHashMap(jenkinsFileHashMap);
        return parse(pipelineNode);
    }

    @Override
    public Optional<PipelineModel> parse(LinkedHashMap pipelineNode)  {
        try {
            this.pipelineModel = PipelineModel.builder()
                    .agent(new AgentParser().parse(pipelineNode))
                    .post(new PostParser().parse(pipelineNode))
                    .environment(new EnvironmentParser().parse(pipelineNode))
                    .tools(new ToolsParser().parse(pipelineNode))
                    .options(new OptionsParser().parse(pipelineNode))
                    .parameters(new ParametersParser().parse(pipelineNode))
                    .triggers(new TriggersParser().parse(pipelineNode))
                    .stages(new StagesParser().parse(pipelineNode))
                    .build();
            return Optional.ofNullable(this.pipelineModel);
        }
        catch (Exception e) {
            throw new PipelineAsYamlRuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public Optional<PipelineModel> parse(ModelASTPipelineDef modelAST) {
        return Optional.empty();
    }

    public static ModelASTPipelineDef parse(String jenkinsFileAsYamlContent){
        return Converter.scriptToPipelineDef(jenkinsFileAsYamlContent);
    }

}
