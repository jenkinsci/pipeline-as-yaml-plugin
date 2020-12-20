package io.jenkins.plugins.pipeline;

import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Action;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlEmptyInputException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlException;
import io.jenkins.plugins.pipeline.exceptions.PipelineAsYamlRuntimeException;
import io.jenkins.plugins.pipeline.models.PipelineModel;
import io.jenkins.plugins.pipeline.parsers.PipelineParser;
import jenkins.model.TransientActionFactory;
import jenkins.security.stapler.StaplerAccessibleType;
import org.jenkinsci.plugins.workflow.cps.Snippetizer;
import org.jenkinsci.plugins.workflow.cps.SnippetizerLink;
import org.kohsuke.stapler.*;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

/**
 * Pipeline As YAML Snippetizer
 */
@Extension
@StaplerAccessibleType
public class PipelineAsYamlSnippetizer extends Snippetizer {

    public static final String snippetizerLink = "payConverter";

    @Override
    public String getUrlName() {
        return snippetizerLink;
    }

    /**
     * JavaScriptMethod implementation converting Pipeline As YAML to Pipeline Declarative Script
     *
     * @param pipelinePay Pipeline As YAML Script
     * @return Pipeline Declarative Script
     */
    @JavaScriptMethod
    public String convertToDec(String pipelinePay) {
        PipelineParser pipelineParser = new PipelineParser(pipelinePay);
        try {
            this.checkConverterInput(pipelinePay);
            Optional<PipelineModel> pipelineModel = pipelineParser.parse();
            if (pipelineModel.isPresent()) {
                return pipelineModel.get().toPrettyGroovy();
            } else {
                throw new PipelineAsYamlRuntimeException("Exception happened while converting. Please check the logs");
            }
        } catch (PipelineAsYamlEmptyInputException p) {
            return "";
        } catch (PipelineAsYamlRuntimeException p) {
            return "Exception happened while converting. Please check the logs";
        }
    }

    /**
     * JavaScriptMethod implementation for Parse and Validate Pipeline As YAML
     *
     * @param pipelinePay Pipeline As YAML Script
     * @return Validation Output
     */
    @JavaScriptMethod
    public String parseAndValidatePay(String pipelinePay) {
        PipelineParser pipelineParser = new PipelineParser(pipelinePay);
        try {
            this.checkConverterInput(pipelinePay);
            Optional<PipelineModel> pipelineModel = pipelineParser.parseAndValidate();
            if (pipelineModel.isPresent()) {
                return "Valid";
            } else {
                throw new PipelineAsYamlException("Pipeline validation failed. Please check the logs");
            }
        } catch (RuntimeException r) {
            return r.getLocalizedMessage();
        } catch (PipelineAsYamlException p) {
            return p.getLocalizedMessage();
        }
    }

    /**
     * Check Input from the Java Script Request
     *
     * @param converterInput Input from Request
     * @throws PipelineAsYamlEmptyInputException
     */
    private void checkConverterInput(String converterInput) throws PipelineAsYamlEmptyInputException {
        if (converterInput == null || converterInput.trim().length() == 0)
            throw new PipelineAsYamlEmptyInputException("Input send from Converter Page is empty");
    }

    /**
     * Extension for {@link TransientActionFactory}
     */
    @Extension
    public static class ActionExtension extends TransientActionFactory<AbstractItem> {

        @Override
        public Class<AbstractItem> type() {
            return AbstractItem.class;
        }

        @Nonnull
        @Override
        public Collection<? extends Action> createFor(@Nonnull AbstractItem abstractItem) {
            return Collections.singleton(new PipelineAsYamlSnippetizer());
        }
    }

    /**
     * Extension for {@link SnippetizerLink}
     */
    @Extension
    public static class LinkExtension extends SnippetizerLink {

        @Nonnull
        @Override
        public String getUrl() {
            return snippetizerLink;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return Messages.Project_SnippetizerDisplayName();
        }
    }
}
