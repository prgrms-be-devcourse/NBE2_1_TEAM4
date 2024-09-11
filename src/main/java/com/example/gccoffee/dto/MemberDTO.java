package com.example.gccoffee.dto;

import com.example.gccoffee.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
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

    private LocalDateTime joinDate;
    private LocalDateTime modifiedDate;

    public MemberDTO(Member member) {
        this.mid = member.getMid();
        this.mpw = member.getMpw();
        this.mname = member.getMname();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.joinDate = member.getJoinDate();
        this.modifiedDate = member.getModifiedDate();
    }

    public Map<String, Object> getPayload() {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("mid", mid);
        payloadMap.put("mpw", mpw);
        payloadMap.put("mname", mname);
        payloadMap.put("email", email);
        payloadMap.put("role", role);

        return payloadMap;
    }
}
