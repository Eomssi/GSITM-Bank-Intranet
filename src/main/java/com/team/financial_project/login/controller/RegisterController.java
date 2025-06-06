package com.team.financial_project.login.controller;

import com.team.financial_project.dto.UserDTO;
import com.team.financial_project.login.service.RegisterService;
import com.team.financial_project.main.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("register")
public class RegisterController {
    private final RegisterService registerService;
    private final S3Service s3Service;

    public RegisterController(RegisterService registerService, S3Service s3Service) {
        this.registerService = registerService;
        this.s3Service = s3Service;
    }

    @GetMapping("")
    public String registerForm(){
        return "login/register";
    }

    @GetMapping("search")
    @ResponseBody
    public List<UserDTO> searchUsers(@RequestParam("keyword") String keyword) {
        return registerService.findUsersByKeyword(keyword);
    }

    @GetMapping("certify")
    @ResponseBody
    public boolean certify_phone(@RequestParam("telno") String telno) {
        System.out.println("####################################3"+telno);
        return registerService.certifyByUserTelno(telno);
    }

    @PatchMapping("")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestParam("user_id") String userId,
                                          @RequestParam("user_pw") String userPw,
                                          @RequestParam("user_email") String userEmail,
                                          @RequestParam("user_telno") String userTelno,
                                          @RequestParam(value = "user_imgpath", required = false) MultipartFile userImgPath) {
        try {
            String fileUrl = null;
            if (userImgPath != null && !userImgPath.isEmpty()) {
                if (!userImgPath.getContentType().startsWith("image/")) {
                    return ResponseEntity.badRequest().body("이미지 파일만 업로드 가능합니다.");
                }
                fileUrl = s3Service.uploadFile(userImgPath);
            }

            UserDTO user = new UserDTO();
            user.setUser_id(userId);
            user.setUser_pw(userPw);
            user.setUser_email(userEmail);
            user.setUser_telno(userTelno);
            user.setUser_imgpath(fileUrl);

            registerService.register(user);
            return ResponseEntity.ok("등록되셨습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 등록 중 오류가 발생했습니다.");
        }
    }
}
