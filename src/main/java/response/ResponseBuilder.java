package response;

import request.IRequest;

import java.util.List;
import java.util.Map;

public class ResponseBuilder {
    private String body;
    private Map<String, List<String>> headers;
    private int code;
    private IRequest request;

    // with all the information even the request object
    // built in the executor class
    public ResponseBuilder withResponseBody(String body){
        this.body = body;
        return this;
    }

    public ResponseBuilder withHeaders(Map<String, List<String>> headers){
        this.headers = headers;
        return this;
    }

    public ResponseBuilder withCode(int code){
        this.code = code;
        return this;
    }

    public ResponseBuilder withRequest(IRequest request) {
        this.request = request;
        return this;
    }

    public IResponse build(){
        return new Response(this.code, this.body, this.headers, this.request);
    }
}
