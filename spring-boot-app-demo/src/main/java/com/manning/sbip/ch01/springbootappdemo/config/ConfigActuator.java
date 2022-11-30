package com.manning.sbip.ch01.springbootappdemo.config;

import com.manning.sbip.ch01.springbootappdemo.config.dto.ReleaseNote;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class ConfigActuator {

    @Bean(name = "releaseNotes")
    public Collection<ReleaseNote> loadReleaseNotes() {
        final Set<ReleaseNote> releaseNotes = new LinkedHashSet<>();
        final ReleaseNote releaseNote1 = ReleaseNote.builder()
                .version("v1.2.1")
                .releaseDate(LocalDate.of(2021,12, 30))
                .commitTag("8ujcnjsdn")
                .bugFixes(Set.of("acerto x", "acerto y").stream().toList())
                .build();

        final ReleaseNote releaseNote2 = ReleaseNote.builder()
                .version("v1.2.0")
                .releaseDate(LocalDate.of(2021,11, 20))
                .commitTag("4049454")
                .bugFixes(Set.of("acerto p", "acerto q").stream().toList())
                .build();

        releaseNotes.addAll(Set.of(releaseNote1, releaseNote2));
        return releaseNotes;
    }
}
