package com.appcloudinary.practice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;

    private Map<String,String> valueMaps = new HashMap<>();

    public CloudinaryService() {
        valueMaps.put("cloud_name","");
        valueMaps.put("api_key","");
        valueMaps.put("api_secret","");
        cloudinary = new Cloudinary(valueMaps);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }
    public Map delete(String idImage) throws IOException {
        Map result = cloudinary.uploader().destroy(idImage,ObjectUtils.emptyMap());
        return result;
    }
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
