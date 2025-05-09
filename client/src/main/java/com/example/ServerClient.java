package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.plugins.providers.multipart.MultipartRelatedOutput;
import java.util.UUID;

@Path("/receive")
public interface ServerClient {
    @POST
    @Consumes("multipart/related; type=application/json")
    @Produces(MediaType.APPLICATION_JSON)
    UUID sendMultipart(MultipartRelatedOutput multipart);
}
