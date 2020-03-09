package org.jenkinsci.plugins.pipeline.yaml.interfaces;

import com.amihaiemil.eoyaml.YamlNode;
import org.jenkinsci.plugins.pipeline.yaml.models.AbstractModel;

import java.util.LinkedHashMap;

public interface ParserInterface<T > {

    T parse();
}
