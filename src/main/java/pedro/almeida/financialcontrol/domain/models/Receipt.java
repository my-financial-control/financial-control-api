package pedro.almeida.financialcontrol.domain.models;

import java.io.InputStream;
import java.util.UUID;

public class Receipt {
    private final UUID transactionId;
    private final String fileName;
    private final String contentType;
    private final InputStream content;
    private final long size;

    public Receipt(UUID transactionId, String fileName, String contentType, InputStream content, long size) {
        this.transactionId = transactionId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
        this.size = size;
    }

    public UUID getTransactionId() {
        return transactionId;
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
