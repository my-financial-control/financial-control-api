package pedro.almeida.financialcontrol.domain.models;

import java.io.InputStream;
import java.util.UUID;

public class Receipt {
    private final UUID id;
    private final String fileName;
    private final String contentType;
    private final InputStream content;
    private final long size;

    public Receipt(UUID id, String fileName, String contentType, InputStream content, long size) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
        this.size = size;
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getContent() {
        return content;
    }

    public long getSize() {
        return size;
    }
}
