package io.javabrains.betterreads;

import com.fasterxml.jackson.core.JacksonException;
import io.javabrains.betterreads.author.Author;
import io.javabrains.betterreads.author.AuthorRepository;
import io.javabrains.betterreads.connection.DataStaxAstraProperties;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterReadsApp {
    @Autowired
    AuthorRepository authorRepository;

    @Value("${datadump.location.author}")
    private String authordumpLocation;
    @Value("${datadump.location.works}")
    private String worksdumpLocation;

    public static void main(String[] args) {
        System.out.println("Application Started0!");
        SpringApplication.run(BetterReadsApp.class, args);
    }

    private void initAuthor() {
        Path path = Paths.get(authordumpLocation);
        try (
                Stream<String> lines = Files.lines(path)) {
            lines.limit(10).forEach(line -> {
                String jsonString = line.substring(line.indexOf("{"));
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Author author = new Author();
                    author.setId(jsonObject.optString("key").replace("/authors/", ""));
                    author.setName(jsonObject.optString("name"));
                    author.setPersonName(jsonObject.optString("personal_name"));
                    System.out.println("saving author: " + author.getName() + "...");
                    authorRepository.save(author);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWorks() {
        Path path = Paths.get(worksdumpLocation);
    }

    @PostConstruct
    public void start() {

        System.out.println("Application Started!");
        Author author = new Author();
        author.setId("id");
        author.setName("Name");
        author.setPersonName("personalName");
        authorRepository.save(author);
        System.out.println(authordumpLocation);
        System.out.println(worksdumpLocation);
        //initAuthor();
        //initWorks();
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        System.out.println("haha2");
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
