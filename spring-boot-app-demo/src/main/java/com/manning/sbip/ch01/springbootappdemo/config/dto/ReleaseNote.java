package com.manning.sbip.ch01.springbootappdemo.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReleaseNote {

    private String version;
    private LocalDate releaseDate;
    private String commitTag;
    private List<String> bugFixes;
}
