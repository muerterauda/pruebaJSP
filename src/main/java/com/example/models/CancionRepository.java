package com.example.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CancionRepository extends MongoRepository<Cancion, String> {

    public List<Cancion> findCancionByAutor(String autor);

    public List<Cancion> findCancionByGenero(String genero);
}