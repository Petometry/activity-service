package com.frameboter.template.rest;

import com.frameboter.template.Application;
import com.frameboter.template.rest.model.Ping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
class PingResourceTest {

    @Autowired
    private PingResource pingResource;

    @Test
    void ping_shouldReturnCurrentTime() {


        HashMap<String, Object> headers = new HashMap<>();
        headers.put("alg", "RS256");

        LocalDateTime beforePing = LocalDateTime.now().minusSeconds(1);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("sub", "456b149c-954b-4f91-82a4-577fcee247ca");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3OUhyTHJvWVZPUXVIRnE2MHNTOW8zWnRKclF0b3VBOUxaaGNVTHFxellnIn0.eyJleHAiOjE3MTA0Mzg5MTksImlhdCI6MTcxMDQzODYxOSwianRpIjoiOTg3ZjBmYWQtOTE5My00ZjBiLTg0ZWUtMWEwYjU0MTU5OWI5IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5yb2JvdGVyNTEyMy5jb20vcmVhbG1zL3JvYm9sb2NrIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQ1NmIxNDljLTk1NGItNGY5MS04MmE0LTU3N2ZjZWUyNDdjYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRlY2huaWNhbCIsInNlc3Npb25fc3RhdGUiOiIwOWNlNDQ5NS1lMTlkLTQ1OWEtYTY2YS04OWIwYmU2ZDE5MTYiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIi8qIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ0ZXN0Iiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXJvYm9sb2NrIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiIwOWNlNDQ5NS1lMTlkLTQ1OWEtYTY2YS04OWIwYmU2ZDE5MTYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlBhc2NhbCBHZWhyaW5nZXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJyb2JvdGVyNTEyMyIsImdpdmVuX25hbWUiOiJQYXNjYWwiLCJmYW1pbHlfbmFtZSI6IkdlaHJpbmdlciIsImVtYWlsIjoicGFzY2FsLmdlaHJpbmdlckBvdXRsb29rLmNvbSJ9.rHho1Ba272D5l0hk0FRlM1e9yZHbusZUtt11QBbOIsbf_40r0HwpSs1ok_JoW5Zw4ZMlExLVZ6GxzjHqvBoXZgzO-V0IeDLMB5xfnGY2YLbyKaOb1p-maaBNU9Yyp-msScdPpWQEUF1ThVGk4dYMbITlKmTc_mfNuO2tWOxTW9HuIGFbccOirGTA8pZw7tE0oFdH9ZUUrXpdOK5Qzjo9JwqHmbhp2M8JCoOU2jyl5dtmSKDE47_9Y8LoO6iYy69LioIDJltv0DSVrNUebQdaJrhTe7SDynwYM_lvNib1lq4svo8VgEfhogBiCeQpiKLptxxuA9LiC8vhLDHcDK0IIA";
        Ping result = pingResource.createPing(new Jwt(token, null, null, headers, claims));

        assertTrue(result.getCreatedAt().isAfter(beforePing));
        assertTrue(result.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}