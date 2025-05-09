package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.*;

import java.io.*;

@Path("/receive")
public class ServerResource {

    @POST
    @Consumes("multipart/related")
    public Response receive(MultipartRelatedInput input) {
        try {
            for (InputPart part : input.getParts()) {
                MediaType mediaType = part.getMediaType();
                System.out.println("Received part: " + mediaType);

                if (mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
                    String json = part.getBody(String.class, null);
                    System.out.println("JSON Part: " + json);
                } else if (mediaType.equals(MediaType.APPLICATION_OCTET_STREAM_TYPE)) {
                    InputStream is = part.getBody(InputStream.class, null);
                    try (FileOutputStream fos = new FileOutputStream("/tmp/received_file.bin")) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                        System.out.println("Binary part saved to /tmp/received_file.bin");
                    }
                }
            }
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
