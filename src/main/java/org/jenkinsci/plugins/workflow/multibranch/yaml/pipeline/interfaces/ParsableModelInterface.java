package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.interfaces;

/**
 * Interface for Model Classes
 */
public interface ParsableModelInterface {

    /**
     * Convert model to Groovy Syntax
     * @return {@link String}
     */
    String toGroovy();

}
