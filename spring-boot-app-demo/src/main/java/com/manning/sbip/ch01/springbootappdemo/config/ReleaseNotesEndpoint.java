package com.manning.sbip.ch01.springbootappdemo.config;

import com.manning.sbip.ch01.springbootappdemo.config.dto.ReleaseNote;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Endpoint(id = "releaseNotes")
@RequiredArgsConstructor
public class ReleaseNotesEndpoint {

    private final Collection<ReleaseNote> releaseNotes;

    @ReadOperation
    public Iterable<ReleaseNote> releaseNotes() {
        return releaseNotes;
    }

    @ReadOperation
    public Object selectCourse(@Selector final String version) {
        final Optional<ReleaseNote> releaseNoteOptional = releaseNotes
                .stream()
                .filter(note -> note.getVersion().equals(version))
                .findFirst();

        if (releaseNoteOptional.isPresent()) {
            return releaseNoteOptional.get();
        }

        return String.format("Não existe essa versao de lancamento: %s", version);
    }

    @DeleteOperation
    public void removeReleaseVersion(@Selector final String version) {
        Optional<ReleaseNote> releaseNoteOptional = releaseNotes
                .stream()
                .filter(n -> n.getVersion().equals(version))
                .findFirst();

        if (releaseNoteOptional.isPresent()) {
            releaseNotes.remove(releaseNoteOptional.get());
        }
    }
}
