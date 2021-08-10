package response;

import request.IRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record Response(int code, String body,
                       Map<String, List<String>> headers,
                       IRequest request) implements IResponse {

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public List<String> getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    @Override
    public IRequest getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Response that = (Response) obj;
        return this.code == that.code &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.headers, that.headers) &&
                Objects.equals(this.request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, body, headers, request);
    }

    @Override
    public String toString() {
        return "Response[" +
                "code=" + code + ", " +
                "body=" + body + ", " +
                "headers=" + headers + ", " +
                "request=" + request + ']';
    }

}
