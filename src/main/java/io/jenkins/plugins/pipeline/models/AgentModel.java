package io.jenkins.plugins.pipeline.models;

import io.jenkins.plugins.pipeline.interfaces.ParsableModelInterface;
import lombok.Getter;

import java.util.List;

/**
 * Model Class for Jenkins Declarative Pipeline Agent Section
 */
@Getter
public class AgentModel extends AbstractModel implements ParsableModelInterface {

    public static final String directive = "agent";
    private String agentType;
    private List<KeyValueModel> agentParameter;

    /**
     * @param agentType      Agent Type
     * @param agentParameter Parameter list for the agent type
     */
    public AgentModel(String agentType, List<KeyValueModel> agentParameter) {
        this.agentType = this.convertAgentType(agentType);
        this.agentParameter = agentParameter;
    }

    /**
     * Convert Label Agent Type to Node Type
     *
     * @param agentType Agent Type
     * @return Agent Type
     */
    private String convertAgentType(String agentType) {
        if (agentType.equals("label"))
            return "node";
        return agentType;
    }

    @Override
    public String toGroovy() {
        StringBuffer groovyString = new StringBuffer()
                .append(directive);
        if (!this.agentType.equals("none") && !this.agentType.equals("any"))
            groovyString.append(this.getDirectiveOpen());
        else
            groovyString.append(" ");
        groovyString.append(this.agentType);
        if (this.agentParameter.size() > 0) {
            groovyString.append(this.getDirectiveOpen());
            if (this.agentType.equals("kubernetes")) {
                for (KeyValueModel keyValueModel : this.agentParameter) {
                    if (keyValueModel.getKey().equals("yaml")) {
                        groovyString.append(keyValueModel.getKey())
                                .append(" \"\"\"\n")
                                .append(keyValueModel.getValue())
                                .append("\n\"\"\"\n");
                    } else {
                        groovyString.append(keyValueModel.toGroovy());
                    }
                }
             } else {
                this.agentParameter.forEach(keyValueModel -> groovyString.append(keyValueModel.toGroovy()));
            }
            groovyString.append(this.getDirectiveClose());
        }
        if (!this.agentType.equals("none") && !this.agentType.equals("any"))
            groovyString.append(this.getDirectiveClose());
        else
            groovyString.append("\n");
        return groovyString.toString();
    }
}
