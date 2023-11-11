package com.desafiojavareact.gerenciadordeprojetos.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;
import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        Clock.systemDefaultZone();
    }
}