package com.example.gccoffee.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    @NotBlank
    private String mid;

    @NotBlank
    private String mpw;

    @NotBlank
    private String mname;

    @NotBlank
    private String email;

    @NotBlank
    private String role;
}
