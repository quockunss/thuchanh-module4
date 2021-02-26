package com.example.demo.service;


import com.example.demo.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CityService {

    Page<City> findAll(Pageable pageable);

    Optional<City> findById(Long id);

    void save(City city);

    void remove(Long id);

    Iterable<City> findAll();

    Page<City> findAllByNameContaining(String name, Pageable pageable);


}
