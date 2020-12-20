package io.jenkins.plugins.pipeline.scm;

import hudson.model.TaskListener;
import jenkins.scm.api.SCMProbeStat;

import java.io.IOException;

/**
 * SCM Source Criteria for {@link org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory}
 */
public class SCMSourceCriteriaForYamlFile {

    public static boolean matches(String localFile, jenkins.scm.api.SCMSourceCriteria.Probe probe, TaskListener taskListener) throws IOException {
        SCMProbeStat stat = probe.stat(localFile);
        switch (stat.getType()) {
            case NONEXISTENT:
                if (stat.getAlternativePath() != null) {
                    taskListener.getLogger().format("      ‘%s’ not found (but found ‘%s’, search is case sensitive)%n", localFile, stat.getAlternativePath());
                } else {
                    taskListener.getLogger().format("      ‘%s’ not found%n", localFile);
                }
                return false;
            case DIRECTORY:
                taskListener.getLogger().format("      ‘%s’ found but is a directory not a file%n", localFile);
                return false;
            default:
                taskListener.getLogger().format("      ‘%s’ found%n", localFile);
                return true;
        }
    }
}
