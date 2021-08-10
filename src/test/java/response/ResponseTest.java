package response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.IRequest;
import request.Request;
import request.RequestBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    private IResponse response;
    private IRequest request;
    @BeforeEach
    void setUp() {
        request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("GET")
                .withHeader("name", "value")
                .withHeader("second", "header")
                .build();
        Map<String, List<String>> headers = new HashMap<>();
        List<String> value = new ArrayList<>();
        value.add("value");
        headers.put("name",value );
        response= new ResponseBuilder()
                .withResponseBody("TestBody")
                .withHeaders(headers)
                .withCode(200)
                .withRequest(request)
                .build();
    }

    @Test
    public void getHeader(){
        assertEquals("value",response.getHeader("name").get(0));

    }

    @Test
    void getCode() {
        assertEquals(200, response.getCode());
    }

    @Test
    void getBody() {
        assertEquals("TestBody", response.getBody());
    }


    @Test
    void getRequest() {
        assertEquals(this.request, response.getRequest());
    }
}
