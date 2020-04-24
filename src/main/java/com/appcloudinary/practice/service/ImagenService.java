package com.appcloudinary.practice.service;

import com.appcloudinary.practice.model.Imagen;
import com.appcloudinary.practice.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ImagenService {
    @Autowired
    ImagenRepository imagenRepository;

    public List<Imagen> list(){
        return imagenRepository.findByOrderById();
    }
    public Optional<Imagen> getOne(int id){
        return imagenRepository.findById(id);
    }
    public void save(Imagen imagen){
        imagenRepository.save(imagen);
    }
    public void delete(int id){
        imagenRepository.deleteById(id);
    }
    public boolean exists(int id){
        return imagenRepository.existsById(id);
    }
}
