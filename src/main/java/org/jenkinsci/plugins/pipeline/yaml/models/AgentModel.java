package org.jenkinsci.plugins.pipeline.yaml.models;

import com.amihaiemil.eoyaml.YamlMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class AgentModel extends AbstractModel implements ParsableModelInterface {

    private String agentType;
    private List<KeyValueModel> agentParameter;

    public AgentModel(String agentType, List<KeyValueModel> agentParameter) {
        this.agentType = this.convertAgentType(agentType);
        this.agentParameter = agentParameter;
    }

    private String convertAgentType(String agentType) {
        if( agentType.equals("label"))
            return "node";
        return agentType;
    }


}
