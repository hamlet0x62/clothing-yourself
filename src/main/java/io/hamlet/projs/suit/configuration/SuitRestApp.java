package io.hamlet.projs.suit.configuration;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/api/")
public class SuitRestApp extends Application {
}
