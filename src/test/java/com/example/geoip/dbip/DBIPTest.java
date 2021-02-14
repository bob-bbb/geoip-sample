package com.example.geoip.dbip;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class DBIPTest {

    @Test
    public void country() throws IOException, GeoIp2Exception {
        ClassPathResource classPathResource = new ClassPathResource("database/dbip/dbip-country-lite-2021-02.mmdb");
        // A File object pointing to your GeoIP2 or GeoLite2 database
        File database = classPathResource.getFile();

        // This creates the DatabaseReader object. To improve performance, reuse
        // the object across lookups. The object is thread-safe.

//        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        // use cache
        DatabaseReader reader = new DatabaseReader.Builder(database).withCache(new CHMCache()).build();

        InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

        // Replace "city" with the appropriate method for your database, e.g.,
        // "country".
        CountryResponse response = reader.country(ipAddress);

        Country country = response.getCountry();
        System.out.println(country.getIsoCode());            // 'US'
        System.out.println(country.getName());               // 'United States'
        System.out.println(country.getNames().get("zh-CN")); // '美国'

    }

    @Test
    public void city() throws IOException, GeoIp2Exception {
        ClassPathResource classPathResource = new ClassPathResource("database/dbip/dbip-city-lite-2021-02.mmdb");
        // A File object pointing to your GeoIP2 or GeoLite2 database
        File database = classPathResource.getFile();

        // This creates the DatabaseReader object. To improve performance, reuse
        // the object across lookups. The object is thread-safe.

//        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        // use cache
        DatabaseReader reader = new DatabaseReader.Builder(database).withCache(new CHMCache()).build();

        InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

        // Replace "city" with the appropriate method for your database, e.g.,
        // "country".
        CityResponse response = reader.city(ipAddress);

        Country country = response.getCountry();
        System.out.println(country.getIsoCode());            // 'US'
        System.out.println(country.getName());               // 'United States'
        System.out.println(country.getNames().get("zh-CN")); // '美国'

        Subdivision subdivision = response.getMostSpecificSubdivision();
        System.out.println(subdivision.getName());    // 'Minnesota'
        System.out.println(subdivision.getIsoCode()); // 'MN'

        City city = response.getCity();
        System.out.println(city.getName()); // 'Minneapolis'

        Postal postal = response.getPostal();
        System.out.println(postal.getCode()); // '55455'

        Location location = response.getLocation();
        System.out.println(location.getLatitude());  // 44.9733
        System.out.println(location.getLongitude()); // -93.2323
    }

    @Test
    public void asn() throws IOException, GeoIp2Exception {
        ClassPathResource classPathResource = new ClassPathResource("database/dbip/dbip-asn-lite-2021-02.mmdb");
        // A File object pointing to your GeoLite2 ASN database
        File database = classPathResource.getFile();

        // This creates the DatabaseReader object. To improve performance, reuse
        // the object across lookups. The object is thread-safe.
        try (DatabaseReader reader = new DatabaseReader.Builder(database).build()) {

            InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

            AsnResponse response = reader.asn(ipAddress);

            System.out.println(response.getAutonomousSystemNumber());       // 217
            System.out.println(response.getAutonomousSystemOrganization()); // 'University of Minnesota'
        }
    }
}
