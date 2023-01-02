package okwrapper;

import client.Client;
import mediatype.HttpMediaType;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import request.IRequest;
import response.IResponse;
import response.ResponseBuilder;

import java.io.IOException;
import java.util.Objects;

public record OKAPIExecutor(IRequest request) implements RequestExecutor {

    @Override
    public IResponse execute() {
        OkHttpClient client = new Client().getClient();
        RequestBody okRequestBody = addRequestBody();
        Call call = client
                .newCall(
                        new Request.Builder()
                                .headers(addRequestHeaders())
                                .url(request.getUrl())
                                .method(request.getMethod(), okRequestBody)
                                .build()
                );
        Response okResponse = executeOKRequest(call);
        assert okResponse != null;
        return buildResponse(okResponse);
    }

    private Headers addRequestHeaders() {
        return Headers.of(request.getHeaders());
    }

    private RequestBody addRequestBody() {
        MediaType mediaType;
        mediaType = MediaType.parse(request.getMediaType().value());
        assert mediaType != null;
        if (request.getMediaType().value().equals(HttpMediaType.MULTIPART_FORM_DATA.getMediaType())) {
            FormBody.Builder formBody = new FormBody.Builder();
            request.getBody().forEach((k, v) -> formBody.add(k, v.toString()));
            return formBody.build();
        }
        if (mediaType.toString().equals(HttpMediaType.TEXT_PLAIN.getMediaType()) ||
                mediaType.toString().equals(HttpMediaType.APPLICATION_OCTET_STREAM.getMediaType())) {
            return RequestBody.create(mediaType, request.getStringBody());
        }
        if(HttpMediaType.isJsonBody(request.getMediaType().value())) {
            JSONObject json = new JSONObject(request.getBody());
            String jsonString = json.toString();
            return RequestBody.create(mediaType, jsonString);

        }
        return null;
    }

    @Nullable
    private Response executeOKRequest(Call call) {
        Response okResponse = null;
        try {
            okResponse = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return okResponse;
    }

    private IResponse buildResponse(Response okResponse) {
        IResponse response = null;
        try {
            response = new ResponseBuilder()
                    .withResponseBody(okResponse.body().string())
                    .withHeaders(okResponse.headers().toMultimap())
                    .withCode(okResponse.code())
                    .withRequest(request)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        OKAPIExecutor that = (OKAPIExecutor) obj;
        return Objects.equals(this.request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "OKAPIExecutor[" +
                "request=" + request + ']';
    }

}
