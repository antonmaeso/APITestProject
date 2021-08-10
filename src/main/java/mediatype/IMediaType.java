package mediatype;

public interface IMediaType {

    IMediaType parse(String mediaTypeName);
    void setMediaType(String mediaTypeName);
    String value();
}
