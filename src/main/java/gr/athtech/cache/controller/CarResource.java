package gr.athtech.cache.controller;


import gr.athtech.cache.dao.Car;
import gr.athtech.cache.rest.CarDto;
import gr.athtech.cache.rest.CarMapper;
import gr.athtech.cache.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@Transactional
public class CarResource {

    private final CarService carService;
    private final CarMapper carMapper;


    @Autowired
    public CarResource(CarService carService, CarMapper carMapper ) {
        this.carService = carService;
        this.carMapper = carMapper;

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    private CarDto createCar(@RequestBody CarDto carDto) {
        Car car = carMapper.toCar(carDto);
        return carMapper.toCarDto(carService.saveCar(car));
    }

    @PutMapping
    private CarDto updateCar(@RequestBody CarDto carDto) {
        Car car = carMapper.toCar(carDto);
        return carMapper.toCarDto(carService.update(car));
    }

    @GetMapping(value = "/{uuid}")
    private CarDto get(@PathVariable UUID uuid){
        System.out.println("The get has been called");
        return carMapper.toCarDto(carService.get(uuid));
    }


    @GetMapping
    private List<CarDto> getCars(){
        List<Car> cars= carService.getCars();
        List<CarDto> carDtos = new ArrayList<>();
        cars.forEach(car->carDtos.add(carMapper.toCarDto(car)));
        return carDtos;
    }



    @DeleteMapping(value = "/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable  UUID uuid){
      carService.delete(uuid);
    }


}
