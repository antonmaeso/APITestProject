package integration;

import okwrapper.OKAPIExecutor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import request.IRequest;
import request.RequestBuilder;
import request.decorators.validation.ValidateRequestDecorator;
import response.IResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataDrivenTestSetupTest {

    @Test
    public void getResponseBody() {
        IRequest request = new ValidateRequestDecorator(new RequestBuilder()
                .withUrl("https://postman-echo.com/post")
                .withMethod("POST")
                .withHeader("name", "value")
                .withMediaType("text/plain")
                .withBody("foo1=bar1&foo2=bar2")
                .build());
        IResponse response = new OKAPIExecutor(request).execute();
        JSONObject json = new JSONObject(response.getBody());
        assertEquals(200, response.getCode());
        assertEquals("foo1=bar1&foo2=bar2",json.get("data"));
        assertEquals("application/json; charset=utf-8", response.getHeader("content-type").get(0))
        ;
    }

}