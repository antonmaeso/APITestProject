package mediatype;

public enum HttpMediaType {
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    private final String mediaType;

    HttpMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }
}
