Pipeline As Yaml Plugin
=======================
This plugin enables defining Jenkins Pipelines in YAML Format. 

# Description
Jenkins enables defining pipelines with specific DSL. With this plugin Jenkins pipelines can be defined in Yaml format.

Defined Yaml format is converted to Jenkins Pipeline Declarative syntax in runtime. 

Any existing steps in [Snipped Generator](https://jenkins.io/doc/book/pipeline/getting-started/#snippet-generator) 
or [Declarative Directive Generator](https://jenkins.io/doc/book/pipeline/getting-started/#directive-generator) can bu used in `step` or `script` block. 

[Jenkins Declarative Pipeline Syntax](https://jenkins.io/doc/book/pipeline/syntax/) rules must be followed. 

Please see below for usage examples.

## Usage
For using Pipeline as Yaml in your MultiBranch Pipeline, select `by Jenkinsfile As Yaml' in `Build Configuration`.

![Build Configuration](./images/screenshot1.png)

## Pipeline As Yaml Syntax

Pipeline definition must stat with `pipeline` key.

[For detailed usage examples please check here.](./src/test/resources/job)
```yaml
pipeline:
  agent:
    any:
    ...
    ...
```

### Agent
Example agent definition is shown below. Agent definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/agent)
```yaml
pipeline:
  agent:
    node:
      label: 'label'
```

### Environment
Example definition is shown below. Environment definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/environment)
```yaml
pipeline:
  environment:
    KEY1: "VAL1"
```

### Options
Example definition is shown below. Options definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/options)
```yaml
pipeline:
  options:
    - "timeout(time: 1, unit: 'HOURS')"
```

### Post
Example definition is shown below. Post definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/post)
```yaml
pipeline:
  post:
    always:
      - echo Test
    changed:
      - echo Test
```

### Tools
Example definition is shown below. Tools definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/tools)
```yaml
pipeline:
  tools:
    maven: "maven"
```

### When
Example definition is shown below. When definitions can be used under `stage` definitions.

[For further supported definitions syntax please check.](./src/test/resources/when)
```yaml
pipeline:
  stages:
    - stage: "WhenTest"
      when:
        - "branch 'production'"
```

### Parameters
Example definition is shown below.

[For further supported definitions syntax please check.](./src/test/resources/parameters)
```yaml
pipeline:
  parameters:
    - "string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')"
```

### Triggers
Example definition is shown below.

[For further supported definitions syntax please check.](./src/test/resources/triggers)
```yaml
pipeline:
  triggers:
    - cron('H */4 * * 1-5')
```

### Stages
Example definition is shown below.

[For further supported definitions syntax please check.](./src/test/resources/stages)
```yaml
pipeline:
  agent:
    none:
  stages:
    - stage: "Stage1"
      steps:
        - echo "1"
    - stage: "Stage2"
      steps:
        - echo "2"
```

```yaml
pipeline:
  agent:
    none:
  stages:
    - stage: "Stage1"
      stages:
        - stage: "Inner Stage1"
          steps:
            - echo "1"
```

```yaml
pipeline:
  stages:
    - stage: "Stage1"
      steps:
        - echo "1"
    - stage: "Parallel"
      parallel:
        - stage: "Parallel1"
          steps:
            - echo "P1"
        - stage: "Parallel2"
          steps:
            - echo "P1"
```

### Steps
Example definition is shown below.

[For further supported definitions syntax please check.](./src/test/resources/steps)
```yaml
pipeline:
  stages:
    - stage: "Stage"
      steps:
        - echo env.WORKSPACE
```

```yaml
pipeline:
  stages:
    - stage: "Stage1"
      steps:
        script:
          - echo "1"
```

For implementing complex scripts or steps

```yaml
pipeline:
  stages:
    - stage: "Stage1"
      steps:
        script: |
          echo "1"
          echo "2"
          echo "3"
```

## Change Log
### Version 0.1.0
First usable version

## Next Release
### Version 0.2.0
[Issues to be implemented](https://github.com/aytuncbeken/pipeline-as-yaml/milestone/2)


