package com.goodstransport.driver.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponseDTO
{
    private String jwtToken;
    private String username;
}
