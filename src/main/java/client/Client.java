package client;

import interceptor.InterceptorRequestLogging;
import okhttp3.OkHttpClient;
import request.IRequest;
//feel like this should exist
public class Client implements IClient{

    public OkHttpClient getClient(){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(new InterceptorRequestLogging())
                .build();
    }

    @Override
    public IClient newCall(IRequest request) {
        return null;
    }
}
