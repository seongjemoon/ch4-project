package com.ch4project.controller;

import com.ch4project.entity.Member;
import com.ch4project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    // 1. 팀원 정보 저장 API (POST /api/members)
    @PostMapping
    public ResponseEntity<Member> saveMember(@RequestBody Member member) {
        log.info("[API - LOG] POST /api/members - name: {}, age: {}, mbti: {}", member.getName(), member.getAge(), member.getMbti());
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    // 2. 전체 팀원 목록 조회 API (GET /api/members)
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        log.info("[API - LOG] GET /api/members - Fetching all members");
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }

    // 3. 특정 팀원 단건 조회 API (GET /api/members/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        log.info("[API - LOG] GET /api/members/{}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀원을 찾을 수 없습니다. id=" + id));
        return ResponseEntity.ok(member);
    }
}