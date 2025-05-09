package com.example;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.util.logging.Logger;

public class ServerClientFactory {

    private static final Logger LOG = Logger.getLogger(ServerClientFactory.class.getName());

    public ServerClient createServerClient() {
        Config config = ConfigProvider.getConfig();
        String baseUri = config.getValue("destination.client.baseUri", String.class);
        if (baseUri == null || baseUri.trim().isEmpty()) {
            LOG.severe("Configuration error: " + "Destination API" + " baseUri is missing or empty.");
            throw new IllegalArgumentException("Missing " + "Destination API" + " base URI configuration");
        }
        RestClientBuilder builder = RestClientBuilder.newBuilder()
                .baseUri(URI.create(baseUri))
                .register(new JacksonConfig());
        return builder.build(ServerClient.class);
    }

}
