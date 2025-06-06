package com.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.Set;

@ApplicationPath("/api")
public class ServerApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(ServerResource.class);
    }
}
