package io.javabrains.betterreads;

import io.javabrains.betterreads.author.Author;
import io.javabrains.betterreads.author.AuthorRepository;
import io.javabrains.betterreads.connection.DataStaxAstraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterReadsApp {
	@Autowired
	AuthorRepository authorRepository;
	public static void main(String[] args) {
		System.out.println("Application Started0!");
		SpringApplication.run(BetterReadsApp.class, args);
	}

	@PostConstruct
	public void start() {

		System.out.println("Application Started!");
		Author author = new Author();
		author.setId("id");
		author.setName("Name");
		author.setPersonName("personalName");
		authorRepository.save(author);
	}
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties){
		System.out.println("haha2");
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder->builder.withCloudSecureConnectBundle(bundle);
	}

}
