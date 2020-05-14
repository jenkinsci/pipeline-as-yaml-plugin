package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Action;
import hudson.model.RootAction;
import hudson.util.HttpResponses;
import jenkins.model.TransientActionFactory;
import org.jenkinsci.plugins.workflow.cps.Snippetizer;
import org.jenkinsci.plugins.workflow.cps.SnippetizerLink;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.WebMethod;
import org.kohsuke.stapler.interceptor.RequirePOST;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

@Extension
public class PipelineAsYamlSnippetizer extends Snippetizer {

    public static final String snippetizerLink = "payConverter";


    @Override
    public String getUrlName() {
        return snippetizerLink;
    }


    @RequirePOST
    public HttpResponse doConvertToPay(@QueryParameter String pipelineDec) {
        return HttpResponses.redirectTo(".");
    }

    @RequirePOST
    public HttpResponse doConvertToDec(@QueryParameter String pipelinePay) {
        return HttpResponses.redirectTo(".");
    }




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
