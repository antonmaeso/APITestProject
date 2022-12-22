package request.decorators;

import mediatype.IMediaType;
import request.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static mediatype.HttpMediaType.X_WWW_FORM_URLENCODED;

public class CurlDecorator extends RequestDecorator{
    private String curl;
    public CurlDecorator(Request request) {
        super(request);
    }

    @Override
    public IMediaType getMediaType() {
        return request.getMediaType();
    }

    @Override
    public String getStringBody() {
        StringBuilder body = new StringBuilder();
        if(request.getMediaType().value().equals(X_WWW_FORM_URLENCODED.getMediaType())){
            String[] params = request.getStringBody().split("&");
            for (String s : params) {
                body.append("--data-urlencode '").append(s).append("' \\\n");
            }
        } else {
            body = new StringBuilder("--data-raw '" + request.getStringBody() + "'");
        }
        return body.toString();
    }

    @Override
    public Map<String, Object> getBody() {
        return null;
    }

    @Override
    public String getUrl() {
        return request.getUrl();
    }

    @Override
    public String getMethod() {
        return request.getMethod();
    }

    @Override
    public Map<String, String> getHeaders() {
        String headers = "";
        HashMap<String, String> newHeaders = new HashMap<>();
        Iterator it = request.getHeaders().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            newHeaders.put(pair.getKey().toString(), "--header '" + pair.getKey() +": "+ pair.getValue()+"'\\\n");
            it.remove();
        }
        return newHeaders;
    }

    public String toCurl() {
        if (curl == null) {
            String headers = "";
            for (Map.Entry<String, String> entry : getHeaders().entrySet())
                headers += entry.getValue();
            curl = "curl --location --request " + getMethod() + " '" + request.getUrl() + "'\\\n" +
                    "--header 'Content-Type: " + getMediaType().value() + "'" + "\\\n" +
                    headers +
                    getStringBody();
        }
       return curl;
    }
}
