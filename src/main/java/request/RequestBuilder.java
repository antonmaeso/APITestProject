package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestBuilder {
    @JsonProperty("url")
    private String url;
    @JsonProperty("method")
    private String method;
    @JsonProperty("body")
    private Map<String,Object> body;
    @JsonProperty("string_body")
    private String stringBody;
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
    public RequestBuilder withBody(Map<String,Object> body) {
        this.body = body;
        return this;
    }

    @JsonProperty("string_body")
    public RequestBuilder withBody(String stringBody) {
        this.stringBody = stringBody;
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
        return new Request(url,method, body, stringBody, mediaType, headers);
    }
}
