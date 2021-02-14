package com.example.geoip;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GeoipSampleApplication {

    public static void main(String[] args) throws IOException, GeoIp2Exception {
        SpringApplication.run(GeoipSampleApplication.class, args);
    }

}
