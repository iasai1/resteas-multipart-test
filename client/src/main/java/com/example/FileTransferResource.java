package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.plugins.providers.multipart.MultipartRelatedOutput;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.nio.file.Paths;

@Path("/send")
@Produces(MediaType.APPLICATION_JSON)
public class FileTransferResource {

    private final ServerClientFactory clientFactory = new ServerClientFactory();

    @POST
    public UUID sendFile() {
        ServerClient destinationApiClient = clientFactory.createServerClient();

        String fileName = "bigfile.bin";
        java.nio.file.Path filePath = Paths.get("/opt/" + fileName);
        try (InputStream in = new FileInputStream(filePath.toFile())) {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("fileName", fileName);
            metadata.put("description", "Test file upload via multipart/related");

            MultipartRelatedOutput multipart = new MultipartRelatedOutput();
            multipart.addPart(metadata, MediaType.APPLICATION_JSON_TYPE);
            multipart.addPart(in, MediaType.APPLICATION_OCTET_STREAM_TYPE, fileName, "binary");

            System.out.println("Sending to destination API...");
            UUID result = destinationApiClient.sendMultipart(multipart);
            System.out.println("Upload done. Result: " + result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
