package com.example.imageupload.controller;

import com.example.demo.entity.ResponseObject;
import com.example.imageupload.services.ImgServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fileupload")
@Slf4j
public class FileUploadController {
    @Autowired
    private ImgServices services;
    @PostMapping()
    public ResponseEntity<ResponseObject>uploadfile(@RequestParam("file")MultipartFile file){
        try {
            String generatedFileName=services.storeFile(file);
            return ResponseEntity.ok().body(
                    new ResponseObject("ok","upload successfully",generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok",e.getMessage(),"")
            );
        }

    }
    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<byte[]>readDetailFile(@PathVariable String fileName){
        try {
            byte[]bytes=services.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping()
    public ResponseEntity<ResponseObject>getUpload(){
        try {
            List<String> urls=services.loadAll().map(path -> {
                String urlPath= MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"readDetailFile",path.getFileName().toString()).build().toUri().toString();
                return urlPath;
            }).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ResponseObject("ok","List file success",urls));

        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseObject("ok","List file failed",""));

        }
    }

}
