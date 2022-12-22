package request.decorators;

import org.junit.jupiter.api.Test;
import request.IRequest;
import request.RequestBuilder;

import static mediatype.HttpMediaType.X_WWW_FORM_URLENCODED;
import static org.junit.jupiter.api.Assertions.*;

class CurlDecoratorTest {

    @Test
    void getMediaTypeTextPlain() {
        IRequest request = new CurlDecorator(new RequestBuilder()
                .withMediaType("text/plain")
                .build());
            assertEquals("text/plain", request.getMediaType().value());
    }

    @Test
    void getMediaTypeWithHeaders() {
        IRequest request = new CurlDecorator(new RequestBuilder()
                .withHeader("Name", "value")
                .build());
        assertEquals("--header 'Name: value'\\\n", request.getHeaders().get("Name"));
    }

    @Test
    void getMediaTypeEncodedURL() {
        IRequest request = new CurlDecorator(new RequestBuilder()
                .withMediaType("application/x-www-form-urlencoded")
                .withBody("foo1=bar1&foo2=bar2")
                .build());
        assertEquals(X_WWW_FORM_URLENCODED.getMediaType(), request.getMediaType().value());
            assertEquals("""
                --data-urlencode 'foo1=bar1' \\
                --data-urlencode 'foo2=bar2' \\
                """, request.getStringBody());


    }

    @Test
    void getUrl() {
        IRequest request = new CurlDecorator(new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .build());
        assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2", request.getUrl());
    }

    @Test
    void getMethod() {
        IRequest request = new CurlDecorator(new RequestBuilder()
                .withMethod("GET")
                .build());
        assertEquals("GET", request.getMethod());
    }

    @Test
    void getBody() {
        CurlDecorator request = new CurlDecorator(new RequestBuilder()
                .withUrl("https://postman-echo.com/post")
                .withMethod("POST")
                .withBody("This is expected to be sent back as part of response body.")
                .withMediaType("text/plain")
                .build());

            assertEquals("--data-raw 'This is expected to be sent back as part of response body.'", request.getStringBody());

    }

    @Test
    void toCurlPlainText(){
        CurlDecorator request = new CurlDecorator(new RequestBuilder()
                .withUrl("https://postman-echo.com/post")
                .withMethod("POST")
                .withBody("This is expected to be sent back as part of response body.")
                .withMediaType("text/plain")
                .build());

            assertEquals("""
                   curl --location --request POST 'https://postman-echo.com/post'\\
                   --header 'Content-Type: text/plain'\\
                   --data-raw 'This is expected to be sent back as part of response body.'""", request.toCurl());

    }

    @Test
    void toCurlencoded(){
        CurlDecorator request = new CurlDecorator(new RequestBuilder()
                .withUrl("https://postman-echo.com/post")
                .withMethod("POST")
                .withHeader("Name", "value")
                .withHeader("Name2", "value2")
                .withMediaType("application/x-www-form-urlencoded")
                .withBody("foo1=bar1&foo2=bar2")
                .build());

            assertEquals("""
                    curl --location --request POST 'https://postman-echo.com/post'\\
                    --header 'Content-Type: application/x-www-form-urlencoded'\\
                    --header 'Name: value'\\
                    --header 'Name2: value2'\\
                    --data-urlencode 'foo1=bar1' \\
                    --data-urlencode 'foo2=bar2' \\
                    """, request.toCurl());

    }
}