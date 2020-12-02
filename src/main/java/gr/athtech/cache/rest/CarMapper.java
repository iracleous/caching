package gr.athtech.cache.rest;


import gr.athtech.cache.dao.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

   public CarDto toCarDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .color(car.getColor())
                .name(car.getName())
                .build();
    }

   public Car toCar(CarDto carDto){
        return Car.builder()
                .color(carDto.getColor())
                .id(carDto.getId())
                .name(carDto.getName())
                .build();
    }
}
