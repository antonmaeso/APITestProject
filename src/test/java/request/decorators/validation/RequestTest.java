package request.decorators.validation;

import org.junit.jupiter.api.Test;
import request.Request;
import request.RequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {

    @Test
    public void createNewRequestWithHeader(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("GET")
                .withHeader("name", "value")
                .build();
        assertEquals("value", request.getHeaders().get("name"));
    }

    @Test
    public void createNewRequestWithMultipleHeaders(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("GET")
                .withHeader("name", "value")
                .withHeader("second", "header")
                .build();
        assertEquals("value", request.getHeaders().get("name"));
        assertEquals("header", request.getHeaders().get("second"));
    }

    @Test
    public void createNewRequestWithURL(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("GET")
                .build();
        assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2", request.getUrl());
    }

    @Test
    public void createNewRequestWithGetMethod(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("GET")
                .build();
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void createNewRequestWithBody(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("POST")
                .withBody("foo1=bar1&foo2=bar2")
                .build();
        assertEquals("foo1=bar1&foo2=bar2", request.getBody());
    }

    @Test
    public void createNewRequestWithMediaTypy(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .withMethod("POST")
                .withBody("foo1=bar1&foo2=bar2")
                .withMediaType("text/plain")
                .build();
        assertEquals("foo1=bar1&foo2=bar2", request.getBody());
    }

}