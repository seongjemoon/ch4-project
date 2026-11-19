package com.ch4project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;

@RestController
public class TeamController {

    private final String prettyTeam;

    public TeamController(@Value("${team-name:}") String teamJson, JsonMapper jsonMapper) {
        if (teamJson.isBlank()) {
            this.prettyTeam = "{}";
        } else {
            Object parsed = jsonMapper.readValue(teamJson, Object.class);
            this.prettyTeam = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsed);
        }
    }

    @GetMapping(value = "/api/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public String team() {
        return prettyTeam;
    }
}