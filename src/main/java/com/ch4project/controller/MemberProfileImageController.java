package com.ch4project.controller;

import com.ch4project.entity.Member;
import com.ch4project.repository.MemberRepository;
import com.ch4project.service.S3ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberProfileImageController {

    private final MemberRepository memberRepository;
    private final S3ProfileImageService s3ProfileImageService;

    @PostMapping("/{id}/profile-image")
    @Transactional
    public ResponseEntity<Map<String, Object>> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));

        String profileImageKey = s3ProfileImageService.uploadProfileImage(id, file);
        member.updateProfileImageKey(profileImageKey);

        String presignedUrl = s3ProfileImageService.createPresignedUrl(profileImageKey);

        return ResponseEntity.ok(Map.of(
                "memberId", member.getId(),
                "profileImageKey", profileImageKey,
                "presignedUrl", presignedUrl,
                "expiresInDays", 7
        ));
    }

    @GetMapping("/{id}/profile-image")
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getProfileImage(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));

        String presignedUrl = s3ProfileImageService.createPresignedUrl(member.getProfileImageKey());

        return ResponseEntity.ok(Map.of(
                "memberId", member.getId(),
                "presignedUrl", presignedUrl,
                "expiresInDays", 7
        ));
    }
}