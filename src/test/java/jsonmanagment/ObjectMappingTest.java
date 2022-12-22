package jsonmanagment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import request.RequestBuilder;
import request.RequestBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMappingTest {

    @TempDir
    File anotherTempDir;

    @Test
    public void test(){
    File file = new File(anotherTempDir,"testfile.json");
    try {
        Files.writeString(file.toPath(), "{\n" +
                "  \"requestBuilders\": [\n" +
                "    {\n" +
                "      \"url\": \"https://postman-echo.com/post\",\n" +
                "      \"method\": \"POST\",\n" +
                "      \"string_body\": \"This is expected to be sent back as part of response body.\",\n" +
                "      \"mediaType\": \"plain/text\",\n" +
                "      \"headers\": {\n" +
                "        \"test\": \"name\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    } catch (IOException e) {
        e.printStackTrace();
    }
    RequestBuilder requestBuilder = new RequestBuilder()
            .withBody("This is expected to be sent back as part of response body.")
            .withUrl("https://postman-echo.com/post")
            .withMethod("POST")
            .withMediaType("plain/text")
            .withHeader("test", "name");
        ObjectMapping<RequestBuilders> objectMapper = new ObjectMapping<>(RequestBuilders.class);
    RequestBuilders requestBuilders =  objectMapper.objectMapper(file);
    assertEquals(requestBuilder.build(),requestBuilders.getRequestBuilders().get(0).build());

}

    @Test
    public void loadEnvironmentPropertiesJSON(){
        ObjectMapping<Map> objectMapper = new ObjectMapping<>(Map.class);
        File file = FilesManager.getFile("src/main/resources/environment/test_env.json");
        Map envProperties =  objectMapper.objectMapper(file);

        assertEquals(envProperties.get("hostname"), "test");
    }
}