package com.appcloudinary.practice.controller;

import com.appcloudinary.practice.dto.Mensaje;
import com.appcloudinary.practice.model.Imagen;
import com.appcloudinary.practice.service.CloudinaryService;
import com.appcloudinary.practice.service.ImagenService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin
public class MainController {

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImagenService imagenService;

    @GetMapping("/list")
    public ResponseEntity<List<Imagen>> list(){
        List<Imagen> imagens = imagenService.list();
        return new ResponseEntity<List<Imagen>>(imagens,HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws Exception{
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
            return new ResponseEntity(new Mensaje("imagen no válida"), HttpStatus.NO_CONTENT);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Imagen imagen =
                new Imagen((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        imagenService.save(imagen);
        return new ResponseEntity<Map>(result,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int  id) throws Exception{
        if (!imagenService.exists(id)){
            return new ResponseEntity(new Mensaje("No existe"),HttpStatus.NOT_FOUND);
        }
        Imagen imagen = imagenService.getOne(id).get();
        cloudinaryService.delete(imagen.getImagenId());
        imagenService.delete(id);
        return new ResponseEntity(new Mensaje("Imagen eliminada"),HttpStatus.OK);
    }
}
