package com.example.demo.controller;

import com.example.demo.model.City;
import com.example.demo.model.Country;
import com.example.demo.service.CityService;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @ModelAttribute("countries")
    public Iterable<Country> countries() {
        return countryService.findAll();
    }

    @GetMapping("/home")
    public ModelAndView showHome(@RequestParam("s") Optional<String> s,
                                 @RequestParam("s1") Optional<String> s1,
                                 @RequestParam("page") Optional<Integer> page,
                                 Pageable pageable) {
        Page<City> cities;
        int pageNum = 0;
        if (page.isPresent() && page.get() > 1) {
            pageNum = page.get() - 1;
        }
        Sort sort = Sort.by("name");
        if (s.isPresent()) {
            pageable = PageRequest.of(pageNum, 8);
            cities = cityService.findAllByNameContaining(s.get(), pageable);
        } else {
            if (s1.isPresent()) {
                pageable = PageRequest.of(pageNum, 8, sort);
                cities = cityService.findAll(pageable);
            } else {
                pageable = PageRequest.of(pageNum, 8);
                cities = cityService.findAll(pageable);
            }
        }
        System.out.println();
        ModelAndView modelAndView = new ModelAndView("city/list");
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createCityForm() {
        ModelAndView modelAndView = new ModelAndView("city/create", "city", new City());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult, Pageable pageable) {
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("city/create");
            return modelAndView;
        }
        cityService.save(city);
        modelAndView = new ModelAndView("city/create");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("message", "New city created successfully");
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showFormEdit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("city/edit");
        Optional<City> city = cityService.findById(id);
        modelAndView.addObject("city", city);
        return modelAndView;
    }


    @PostMapping("/edit")
    public ModelAndView editCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult) {
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("city/create");
            return modelAndView;
        }
        cityService.save(city);
        modelAndView = new ModelAndView("city/create");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("message", " City editted successfully");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String deleteCity(@PathVariable("id") Long id) {
        cityService.remove(id);
        return "redirect:/list";

    }

    @GetMapping("/info/{id}")
    public ModelAndView infoCity(@PathVariable("id") Long id){
        ModelAndView modelAndView=new ModelAndView("city/citydetail");
        Optional<City> city= cityService.findById(id);
        modelAndView.addObject("city",city);
        return modelAndView;
    }
}
