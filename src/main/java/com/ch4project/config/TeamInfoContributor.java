package com.ch4project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@Component
public class TeamInfoContributor implements InfoContributor {

    private final Object team;

    public TeamInfoContributor(@Value("${team-name:}") String teamJson, JsonMapper jsonMapper) {
        this.team = teamJson.isBlank() ? Map.of() : jsonMapper.readValue(teamJson, Object.class);
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("team", team);
    }
}