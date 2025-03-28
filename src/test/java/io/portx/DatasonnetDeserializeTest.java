package io.portx;

import com.datasonnet.Mapper;
import com.datasonnet.document.DefaultDocument;
import com.datasonnet.document.Document;
import com.datasonnet.document.MediaTypes;
import io.portx.model.Person;
import io.portx.model.Pet;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

public class DatasonnetDeserializeTest {



    @Test
    public void shouldDeserializeOptionalFieldByDatasonnet() throws URISyntaxException, IOException, JSONException {
        Person person = new Person("John", 18, Optional.of(new Pet("Rufus", 4)));
        String file = readFileAsString("mappings/deserializeOptional.ds");
/*
        Mapper mapper = new Mapper(file);
        DefaultDocument<Person> personDefaultDocument = new DefaultDocument<>(person, MediaTypes.APPLICATION_JAVA);
        String content = mapper.transform(personDefaultDocument, new HashMap<>(), MediaTypes.APPLICATION_JSON).getContent();
//        String content = mapper.transform(new DefaultDocument<>(personDefaultDocument, MediaTypes.APPLICATION_JSON)).getContent();
        Assertions.assertNotNull(content);
        JSONAssert.assertEquals(readFileAsString("personJson.json"), content, true);
        System.out.println(content);*/


        Document<Person> data = new DefaultDocument<>(person, MediaTypes.APPLICATION_JAVA);
        String mapping = readFileAsString("mappings/deserializeOptional.ds");
        Mapper mapper = new Mapper(mapping);
        String mapped = mapper.transform(data, new HashMap<>(), MediaTypes.APPLICATION_JSON).getContent();

        String expectedPersonResponse = readFileAsString("personJson.json");

        Assertions.assertNotNull(expectedPersonResponse);
        JSONAssert.assertEquals(expectedPersonResponse, mapped, true);
        System.out.println(mapped);
        person.setPet(Optional.empty());
        data = new DefaultDocument<>(person, MediaTypes.APPLICATION_JAVA);
        mapped = mapper.transform(data, new HashMap<>(), MediaTypes.APPLICATION_JSON).getContent();
        Assertions.assertNotNull(mapped);

    }

    public static String readFileAsString(String filePath) throws URISyntaxException, IOException {
        Path path = Paths.get(DatasonnetDeserializeTest.class.getClassLoader()
                .getResource(filePath).toURI());
        return new String(Files.readAllBytes(path));
    }
}
