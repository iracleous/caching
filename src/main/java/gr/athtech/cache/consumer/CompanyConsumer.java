package gr.athtech.cache.consumer;

import gr.athtech.cache.rest.CarDto;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CompanyConsumer {
    public static void main(String[] args) {
        final String uri = "http://localhost:4000/cars";

        //TODO: Autowire the RestTemplate in all the examples
        RestTemplate restTemplate = new RestTemplate();

        List<CarDto> result = restTemplate.getForObject(uri, List.class);
        System.out.println(result);


        System.out.println("-----------------------------");

         CarDto carDto = CarDto.builder()
                 .name("MyCar")
                 .color("Green")
                 .build();

        carDto = restTemplate.postForObject(uri, carDto, CarDto.class);

        System.out.println(carDto);
    }
}
