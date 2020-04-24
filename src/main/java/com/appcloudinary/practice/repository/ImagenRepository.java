package com.appcloudinary.practice.repository;


import com.appcloudinary.practice.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,Integer> {
   List<Imagen> findByOrderById();
}
