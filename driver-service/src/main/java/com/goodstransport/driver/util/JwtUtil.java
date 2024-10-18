package com.goodstransport.driver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {


    private final String secret = "rivbreijvnirejnfvirnviourhviunriovnrionfvirnviuhrniuhUJBODNHOIDHIYDBHUJDHVUDIUDHIHDVBOJDVIDHYBDJKDBVUDHBJDVHVDBDJODNBV47r9834yr98yr9rg483g9b4ef9g4rf87grfih498fh";

    // Method to extract all claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to extract a specific claim (driverId in this case)
    public String extractDriverId(String token) {
        return extractAllClaims(token).get("driverId", String.class);
    }
}
