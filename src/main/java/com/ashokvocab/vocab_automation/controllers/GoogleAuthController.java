// src/main/java/com/ashokvocab/vocab_automation/controllers/GoogleAuthController.java
package com.ashokvocab.vocab_automation.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    // (Optional) Endpoint to generate Google OAuth2 URL
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=openid%20email%20profile";
        return ResponseEntity.ok(Map.of("url", url));
    }

    // Receives code from frontend, exchanges for tokens, returns user info or JWT
   @PostMapping("/callback")
   public ResponseEntity<?> callback(@RequestBody Map<String, String> body) {
       String code = body.get("code");
       if (code == null) return ResponseEntity.badRequest().body("Missing code");

       // Exchange code for tokens
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       var params = new org.springframework.util.LinkedMultiValueMap<String, String>();
       params.add("code", code);
       params.add("client_id", clientId);
       params.add("client_secret", clientSecret);
       params.add("redirect_uri", redirectUri);
       params.add("grant_type", "authorization_code");

       HttpEntity<?> request = new HttpEntity<>(params, headers);
       ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
               "https://oauth2.googleapis.com/token", request, Map.class);

       if (!tokenResponse.getStatusCode().is2xxSuccessful() || tokenResponse.getBody() == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get tokens from Google");
       }

       String accessToken = (String) tokenResponse.getBody().get("access_token");

       // Fetch user info
       HttpHeaders userHeaders = new HttpHeaders();
       userHeaders.setBearerAuth(accessToken);
       HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);

       ResponseEntity<Map> userInfo = restTemplate.exchange(
               "https://www.googleapis.com/oauth2/v2/userinfo",
               HttpMethod.GET, userRequest, Map.class);

       if (!userInfo.getStatusCode().is2xxSuccessful() || userInfo.getBody() == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch user info");
       }

       String email = (String) userInfo.getBody().get("email");
       if (email == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found in user info");
       }

       String username = (String) userInfo.getBody().get("name");
       SecretKey key = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

       String jwt = Jwts.builder()
               .setSubject(email)
               .claim("username", username)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
               .signWith(key, SignatureAlgorithm.HS256)
               .compact();

       return ResponseEntity.ok(Map.of("token", jwt));
   }
}