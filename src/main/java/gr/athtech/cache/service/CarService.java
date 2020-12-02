package gr.athtech.cache.service;


import gr.athtech.cache.dao.Car;
import gr.athtech.cache.dao.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CacheManager cacheManager;

    @Autowired
    public CarService(CarRepository carRepository, CacheManager cacheManager) {
        this.carRepository = carRepository;
        this.cacheManager = cacheManager;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @CachePut(value = "cars", key = "#car.id")
    public Car update(Car car) {
        if (carRepository.existsById(car.getId())) {
            return carRepository.save(car);
        }
        throw new IllegalArgumentException("A car must have an id to be updated");
    }

    @Cacheable(value = "cars")
    public Car get(UUID uuid) {
        try
        {
            System.out.println("Going to sleep for 5 Secs.. to simulate backend call.");
            Thread.sleep(1000*5);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return carRepository.findById(uuid)
                .orElseThrow(() -> new IllegalStateException("car with id " + uuid + " was not found"));
    }


   // @Cacheable(value = "allcars")
    public List<Car> getCars() {
        return carRepository.findAll();
    }


    @CacheEvict(value = "cars", key = "#uuid")
    public void delete(UUID uuid) {
        carRepository.deleteById(uuid);
    }

}
