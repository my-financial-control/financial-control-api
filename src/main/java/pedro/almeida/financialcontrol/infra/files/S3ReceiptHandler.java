package pedro.almeida.financialcontrol.infra.files;

import org.springframework.stereotype.Service;
import pedro.almeida.financialcontrol.domain.errors.ReceiptException;
import pedro.almeida.financialcontrol.domain.models.Receipt;
import pedro.almeida.financialcontrol.domain.services.interfaces.Storage;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class S3ReceiptHandler implements Storage {
    private static S3AsyncClient s3AsyncClient;
    private static final String S3_RECEIPTS_BUCKET = System.getenv("S3_RECEIPTS_BUCKET");

    public static S3AsyncClient getAsyncClient() {
        if (s3AsyncClient == null) {
            SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                    .maxConcurrency(50)
                    .connectionTimeout(Duration.ofSeconds(60))
                    .readTimeout(Duration.ofSeconds(60))
                    .writeTimeout(Duration.ofSeconds(60))
                    .build();

            ClientOverrideConfiguration overrideConfig = ClientOverrideConfiguration.builder()
                    .apiCallTimeout(Duration.ofMinutes(2))
                    .apiCallAttemptTimeout(Duration.ofSeconds(90))
                    .retryStrategy(RetryMode.STANDARD)
                    .build();

            s3AsyncClient = S3AsyncClient.builder()
                    .region(Region.US_EAST_1)
                    .httpClient(httpClient)
                    .overrideConfiguration(overrideConfig)
                    .build();
        }
        return s3AsyncClient;
    }

    @Override
    public void save(Receipt file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(S3_RECEIPTS_BUCKET)
                .key(file.getTransactionId().toString())
                .contentType(file.getContentType())
                .metadata(Map.of("filename", file.getFileName()))
                .build();

        try {
            S3AsyncClient client = getAsyncClient();
            CompletableFuture<PutObjectResponse> response = client.putObject(objectRequest, AsyncRequestBody.fromBytes(file.getContent().readAllBytes()));
            response.whenComplete((resp, ex) -> {
                if (ex != null) {
                    throw ReceiptException.uploadError(file.getFileName());
                }
            });
        } catch (Exception e) {
            throw ReceiptException.uploadError(file.getFileName());
        }
    }

    @Override
    public Receipt find(UUID id) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(S3_RECEIPTS_BUCKET)
                .key(id.toString())
                .build();

        try {
            S3AsyncClient client = getAsyncClient();
            CompletableFuture<ResponseBytes<GetObjectResponse>> responseFuture = client.getObject(objectRequest, AsyncResponseTransformer.toBytes());

            ResponseBytes<GetObjectResponse> objectBytes = responseFuture.get();
            GetObjectResponse s3Response = objectBytes.response();
            byte[] data = objectBytes.asByteArray();
            InputStream contentStream = new ByteArrayInputStream(data);

            String contentType = s3Response.contentType() != null ? s3Response.contentType() : "application/octet-stream";
            String fileName = s3Response.metadata() != null
                    ? s3Response.metadata().getOrDefault("filename", id.toString())
                    : id.toString();
            long size = s3Response.contentLength();

            return new Receipt(id, fileName, contentType, contentStream, size);
        } catch (Exception e) {
            throw ReceiptException.loadError(id);
        }
    }
}
