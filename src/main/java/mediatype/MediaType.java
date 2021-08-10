package mediatype;

public class MediaType implements IMediaType{
    private String mediaTypeName;

    @Override
    public IMediaType parse(String mediaTypeName) {
      this.mediaTypeName = mediaTypeName;
        return this;
    }

    @Override
    public void setMediaType(String mediaTypeName) {
        this.mediaTypeName = mediaTypeName;
    }

    @Override
    public String value() {
        return this.mediaTypeName;
    }
}
