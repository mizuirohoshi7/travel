package travel.infra;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/members")
@Transactional
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @PostMapping("/members/login")
    public ResponseEntity<Member> loginMember(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");  // 요청에서 이메일을 추출
        System.out.println("이메일은요: " + email);
        
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        return ResponseEntity.ok(member); // 로그인 성공 시 Member 객체 반환
    }
    
}
//>>> Clean Arch / Inbound Adaptor
