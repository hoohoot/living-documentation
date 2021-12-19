# Hoohoot Living Documentation

⚠️ This is a work in progress ⚠️

A living documentation annotation processor based on Cyrille Martraire's [livingdocumentation-workshop](https://github.com/cyriux/livingdocumentation-workshop). 

## Why ?

Writing boilerplate tests case to generate diagrams and glossary on every project is a bummer. 
Why not have a lib that handle this at compile time instead ? 

## Usage
In your `pom.xml` add the `living-documentation` annotations as a dependency and the annotation-processor to the
`maven-compiler-plugin` : 

```xml
<dependencies>
    <dependency>
        <groupId>org.hoohoot.livingdocumentation</groupId>
        <artifactId>living-documentation</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
<build>
<plugins>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
      <annotationProcessorPaths>
        <path>
          <groupId>org.hoohoot.livingdocumentation</groupId>
          <artifactId>annotation-processor</artifactId>
          <version>1.0-SNAPSHOT</version>
        </path>
      </annotationProcessorPaths>
    </configuration>
  </plugin>
</plugins>
</build>
```

Document your domain with the needed annotations : 
```java
@DomainEntity(description = """
        Pokémon are creatures of all shapes and sizes who live in the wild or alongside humans. For the most part,
        Pokémon do not speak except to utter their names. Pokémon are raised and commanded by their owners (called “Trainers”).
        During their adventures, Pokémon grow and become more experienced and even, on occasion, evolve into stronger Pokémon.
        There are currently more than 700 creatures that inhabit the Pokémon universe.
        """,

        link = { "https://en.wikipedia.org/wiki/List_of_Pok%C3%A9mon_characters" }
)
public class Pokemon {
    Integer hitPoints;
    Integer attack;
    Integer defense;
}
```

Documentation will be generated during the compile phase and accessible under `target/living-documentation` 


## TODO :
- [x] Glossary
- [x] Wordcloud
- [ ] Guided tour
- [ ] Living diagram
- [ ] System diagram
- mvn plugin to serve everything (maybe with jekyll, hugo, Asciidoctor J or Maven Doxia )