package integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import request.Request;
import request.decorators.CurlDecorator;
import response.IResponse;
import testng.BeforeTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataDrivenTestSetupTest {

    @Test
    void testTempDir(@TempDir File tempDir) throws IOException {
        File testFile = new File(tempDir, "test.txt");
        testFile.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("Hello, world!");
        writer.close();

        assertTrue(testFile.exists());
        assertTrue(testFile.canRead());
        assertTrue(testFile.canWrite());
    }
    @Test
    public void getResponseBody(@TempDir File tempDir) throws IOException {
        File testFile = new File(tempDir, "test.json");
        testFile.createNewFile();
        assertTrue(testFile.exists());
        assertTrue(testFile.canWrite());
        assertTrue(testFile.canRead());

        try {
            Files.writeString(testFile.toPath(), "{\n" +
                    "  \"requestBuilders\": [\n" +
                    "    {\n" +
                    "      \"url\": \"https://postman-echo.com/post\",\n" +
                    "      \"method\": \"POST\",\n" +
                    "      \"string_body\": \"This is expected to be sent back as part of response body.\",\n" +
                    "      \"mediaType\": \"text/plain\",\n" +
                    "      \"headers\": {\n" +
                    "        \"test\": \"name\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = testFile.toString();
        new BeforeTest().beforeTest(path,"");
        IResponse response = BeforeTest.getResponse();
        String request = new CurlDecorator((Request) response.getRequest()).toCurl();
        assertEquals(200, response.getCode());
        //assertEquals("foo1=bar1&foo2=bar2",new JSONObject(response.getBody()));
        assertEquals("application/json; charset=utf-8", response.getHeader("content-type").get(0))
        ;
    }

}