package client;

import interceptor.InterceptorModifyRequest;
import okhttp3.OkHttpClient;
import request.IRequest;
//feel like this should exist
public class Client implements IClient{

    public OkHttpClient getClient(){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(new InterceptorModifyRequest())
                .build();
    }

    @Override
    public IClient newCall(IRequest request) {
        return null;
    }
}
