package io.jenkins.plugins.pipeline.interfaces;

/**
 * Interface for Model Classes
 */
public interface ParsableModelInterface {

    /**
     * Convert model to Jenkins Declarative Pipeline Syntax
     *
     * @return Jenkins Declarative Pipeline Syntax
     */
    String toGroovy();

}
