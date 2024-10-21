package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String mid;
    private String mpw;
    private String role;

    public MemberDTO(Member member){
        this.mid = member.getMid();
        this.mpw = member.getMpw();
        this.role = member.getRole();
    }

    //JWT 문자열의 내용 반환
    public Map<String, Object> getPayLoad(){
        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("mid", mid);
        payLoad.put("mpw", mpw);
        payLoad.put("role", role);

        return payLoad;
    }
}
