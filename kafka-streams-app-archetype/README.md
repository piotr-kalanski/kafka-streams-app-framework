# Maven archetype

Maven archetype for generating project template using Kafka Streams Application framework.

## Generating project

To generate project execute:

    mvn archetype:generate -DarchetypeGroupId=com.github.piotr-kalanski -DarchetypeArtifactId=kafka-streams-app-archetype -DarchetypeVersion=0.1-SNAPSHOT -DgroupId=com.example -DartifactId=kafka-streams-example-app
    
For ```groupId``` and ```artifactId``` put your group and artifact id.