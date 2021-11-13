package io.javabrains.betterreads;


import io.javabrains.betterreads.author.Author;
import io.javabrains.betterreads.author.AuthorRepository;
import io.javabrains.betterreads.book.Book;
import io.javabrains.betterreads.book.BookRepository;
import io.javabrains.betterreads.connection.DataStaxAstraProperties;
import org.json.JSONArray;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterReadsApp {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        int number = 0;
        try (Stream <String> lines = Files.lines(path)){
            lines.limit(9).forEach(line->{
                String jsonString = line.substring(line.indexOf("{"));
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    //construct Book object
                    Book book = new Book();
                    //0. book id
                    book.setId(jsonObject.optString("key").replace("/works/", ""));
                    //1. book name
                    book.setName(jsonObject.optString("title"));

                    //2. book description
                    JSONObject descriptionObj = jsonObject.optJSONObject("description");
                    if (descriptionObj != null){
                        book.setDescription(descriptionObj.optString("value"));
                    }
                    //3. publish time
                    JSONObject publishedObj = jsonObject.optJSONObject("created");
                    if (publishedObj != null){
                        String dataStr = publishedObj.getString("value");
                        //System.out.println("data value: " + dataStr);
                        book.setPublishedDate(LocalDate.parse(dataStr, dtf));
                    }
                    //4. cover ids
                    JSONArray coversJSONArr = jsonObject.optJSONArray("covers");
                    if (coversJSONArr != null){
                        List<String> coverIds = new ArrayList<>();
                        for (int i = 0; i < coversJSONArr.length(); i++){
                            coverIds.add(coversJSONArr.getString(i));
                        }
                        book.setCoverIds(coverIds);
                    }
                    //5. authors
                    JSONArray authorJSONArr = jsonObject.optJSONArray("authors");
                    if (authorJSONArr != null) {
                        List<String> authorIds = new ArrayList<>();
                        for (int i = 0; i < authorJSONArr.length();i ++){
                            String authorId = authorJSONArr.getJSONObject(i).getJSONObject("author").getString("key")
                                    .replace("/authors/", "");
                            authorIds.add(authorId);
                        }
                        //5.1 author id
                        book.setAuthorIds(authorIds);
                        List<String> authorNames = authorIds.stream().map(id->authorRepository.findById(id))
                            .map(optionAuthor->{
                                if (!optionAuthor.isPresent()){
                                    return "Unknown Author";
                                }
                                return optionAuthor.get().getName();
                            }).collect(Collectors.toList());
                        //5.1 author name
                        book.setAuthorNames(authorNames);
                    }
                    System.out.println("save book: " + book.getName());
                    bookRepository.save(book);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
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
        initWorks();
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        System.out.println("haha2");
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
