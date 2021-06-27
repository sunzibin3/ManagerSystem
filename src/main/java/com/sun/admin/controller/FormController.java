package com.sun.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

@Slf4j
@Controller
public class FormController {

    @GetMapping(value = {"/form_layouts","/form_layouts.html"})
    public String getFormLayout(){

        return "/form/form_layouts";
    }

    /**
     * MultipartFile自动封装上传的文件
     * @param email
     * @param password
     * @param headerImg
     * @param photos
     * @return
     */
    @PostMapping("/upload")
    public String upload(String email,
                         @RequestParam("password") String password,
                         @RequestPart("headerImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {
        log.info("上传的信息{}，{}，{}，{}",email,password,headerImg.getSize(),photos.length );
        if (!headerImg.isEmpty()){
            String originalFilename = headerImg.getOriginalFilename();
//            InputStream inputStream = headerImg.getInputStream();
            headerImg.transferTo(new File("D:\\"+originalFilename));
        }
        String filename;
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()){
                filename = photo.getOriginalFilename();
                File file =new File("D:\\"+filename);
                if (file.exists()){
                    file=new File("D:\\"+filename+new SimpleDateFormat("yyyy-MM-dd"));
                }
                photo.transferTo(file);
            }
        }
        return "main";
    }
}
