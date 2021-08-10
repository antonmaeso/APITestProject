package request;

import java.util.HashMap;

public class RequestBuilder {

    private String url;
    private String method;
    private String body;
    private String mediaType;
    private HashMap<String, String> headers =new HashMap<>();


    public RequestBuilder withUrl(String url) {
        this.url=url;
        return this;
    }


    public RequestBuilder withMethod(String method) {
        this.method = method;
        return this;
    }
    public RequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public RequestBuilder withMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public RequestBuilder withHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Request build() {
        return new Request(url,method, body, mediaType, headers);
    }
}
