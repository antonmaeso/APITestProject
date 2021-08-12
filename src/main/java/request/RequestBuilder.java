package request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class RequestBuilder {
    @JsonProperty("url")
    private String url;
    @JsonProperty("method")
    private String method;
    @JsonProperty("body")
    private String body;
    @JsonProperty("mediaType")
    private String mediaType;
    @JsonProperty("headers")
    private HashMap<String, String> headers =new HashMap<>();

    @JsonProperty("url")
    public RequestBuilder withUrl(String url) {
        this.url=url;
        return this;
    }

    @JsonProperty("method")
    public RequestBuilder withMethod(String method) {
        this.method = method;
        return this;
    }
    @JsonProperty("body")
    public RequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }
    @JsonProperty("media")
    public RequestBuilder withMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @JsonProperty("headers")
    public RequestBuilder setHeaders(HashMap<String ,String> headers) {
        this.headers = headers;
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
