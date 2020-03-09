package org.jenkinsci.plugins.pipeline.yaml.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jenkinsci.plugins.pipeline.yaml.interfaces.ParsableModelInterface;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeyValueModel extends AbstractModel implements ParsableModelInterface {
    private String key;
    private String value;
}
