package com.example.gccoffee.dto.member;

import com.example.gccoffee.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private String mid;
    private String mpw;
    private String mname;
    private String email;
    private String role;
    private LocalDateTime joinDate;
    private LocalDateTime modifiedDate;

    public MemberResponseDTO(Member member) {
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
