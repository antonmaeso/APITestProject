package request;

import mediatype.IMediaType;
import mediatype.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record Request(String url, String method, Map<String,Object> body, String stringBody, String mediaType,
                      HashMap<String, String> headers) implements IRequest {

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return this.method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, Object> getBody() {
        return this.body;
    }

    public String getStringBody(){
        return this.stringBody;
    }

    public IMediaType getMediaType() {
        return new MediaType().parse(this.mediaType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Request that = (Request) obj;
        return Objects.equals(this.url, that.url) &&
                Objects.equals(this.method, that.method) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.stringBody, that.stringBody) &&
                Objects.equals(this.mediaType, that.mediaType) &&
                Objects.equals(this.headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, body, mediaType, headers);
    }

    @Override
    public String toString() {
        String bodyUsed = null;
        if(body != null){
            bodyUsed = body.toString();
        }
        if (stringBody != null){
            bodyUsed = stringBody;
        }
        return "Request[" +
                "url=" + url + ", " +
                "method=" + method + ", " +
                "body=" + bodyUsed + ", " +
                "mediaType=" + mediaType + ", " +
                "headers=" + headers + ']';
    }

}
