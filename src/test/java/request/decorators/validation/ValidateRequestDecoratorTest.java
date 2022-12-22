package request.decorators.validation;

import org.junit.jupiter.api.Test;
import request.IRequest;
import request.Request;
import request.RequestBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ValidateRequestDecoratorTest {

    @Test
    void onlyMediaTypeAndNoBody() {
//        Request r = (Request) mock(IRequest.class);
        IRequest request = new RequestBuilder()
                    .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                    .withMediaType("plain/text")
                    .withMethod("GET")
                    .build();
//        assertThrows(RequestValidationException.class,() ->{
//           new ValidateRequestDecorator(request).getBody();
//        });
    }

    @Test
    void onlyBodyNoMediaType(){
        Request request = new RequestBuilder()
                .withUrl("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
//                .withBody("fdsfds")
                .withMethod("GET")
                .build();
//        assertThrows(ValidateRequestDecorator.RequestValidationException.class,() ->{
//            new ValidateRequestDecorator(request).getMediaType();
//        });
    }

}