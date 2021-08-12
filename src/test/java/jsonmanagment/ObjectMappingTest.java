package jsonmanagment;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;
import request.Request;
import request.RequestBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;

class ObjectMappingTest {

    @TempDir
    File anotherTempDir;

    @Test
    public void test(){
    File file = new File(anotherTempDir,"testfile.json");
    try {
        Files.writeString(file.toPath(), "[\n" +
                "  {\n" +
                "    \"url\": \"https://postman-echo.com/post\",\n" +
                "    \"method\": \"POST\",\n" +
                "    \"body\": \"This is expected to be sent back as part of response body.\",\n" +
                "    \"mediaType\": \"plain/text\",\n" +
                "    \"headers\": {\"test\": \"name\"}\n" +
                "}\n" +
                "]");
    } catch (IOException e) {
        e.printStackTrace();
    }
    RequestBuilder requestBuilder = new RequestBuilder()
            .withBody("This is expected to be sent back as part of response body.")
            .withUrl("https://postman-echo.com/post")
            .withMethod("POST")
            .withMediaType("plain/text")
            .withHeader("test", "name");
        ObjectMapping<RequestBuilder> objectMapper = new ObjectMapping<>();
    List<RequestBuilder> requestBuilders =  objectMapper.objectMapper(file, RequestBuilder.class);
    assertEquals(requestBuilder.build(),requestBuilders.get(0).build());

}
}