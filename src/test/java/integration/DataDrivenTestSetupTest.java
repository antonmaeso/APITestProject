package integration;

import okwrapper.OKAPIExecutor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import request.IRequest;
import request.Request;
import request.RequestBuilder;
import request.decorators.CurlDecorator;
import request.decorators.validation.ValidateRequestDecorator;
import response.IResponse;
import testng.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataDrivenTestSetupTest {
    @TempDir
    File anotherTempDir;

    @Test
    public void getResponseBody() {
        File file = new File(anotherTempDir,"testfile.json");
        try {
            Files.writeString(file.toPath(), "{\n" +
                    "  \"requestBuilders\": [\n" +
                    "    {\n" +
                    "      \"url\": \"https://postman-echo.com/post\",\n" +
                    "      \"method\": \"POST\",\n" +
                    "      \"body\": \"This is expected to be sent back as part of response body.\",\n" +
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
        String path = file.toPath().toString();
        new BeforeTest().beforeTest(path,"");
        IResponse response = BeforeTest.getResponse();
        String request = new CurlDecorator((Request) response.getRequest()).toCurl();
        assertEquals(200, response.getCode());
        //assertEquals("foo1=bar1&foo2=bar2",new JSONObject(response.getBody()));
        assertEquals("application/json; charset=utf-8", response.getHeader("content-type").get(0))
        ;
    }

}