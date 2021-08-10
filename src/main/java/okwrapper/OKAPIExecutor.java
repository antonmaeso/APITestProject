package okwrapper;

import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import request.IRequest;
import response.IResponse;
import response.ResponseBuilder;

import java.io.IOException;
import java.util.Objects;

public record OKAPIExecutor(IRequest request) implements RequestExecutor {

    @Override
    public IResponse execute() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody okRequestBody = addRequestBody();
        Call call = client.newCall(new Request.Builder().url(request.getUrl()).method(request.getMethod(), okRequestBody).build());
        Response okResponse = executeOKRequest(call);
        assert okResponse != null;
        return buildResponse(okResponse);
    }

    private RequestBody addRequestBody() {
        MediaType mediaType;
        RequestBody okRequestBody = null;
        mediaType = MediaType.parse(request.getMediaType().value());
        okRequestBody = RequestBody.create(mediaType, request.getBody());

        return okRequestBody;
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
