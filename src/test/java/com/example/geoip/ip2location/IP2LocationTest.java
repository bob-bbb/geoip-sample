package com.example.geoip.ip2location;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

public class IP2LocationTest {

    @Test
    public void db1() {
        IP2Location loc = new IP2Location();
        try
        {
            ClassPathResource classPathResource = new ClassPathResource("database/ip2location/IP2LOCATION-LITE-DB1.BIN");

            String ip = "8.8.8.8";
            String binfile = classPathResource.getURL().getPath();
            // loc.IPDatabasePath = binfile;
            loc.Open(binfile, true);

            IPResult rec = loc.IPQuery(ip);
            if ("OK".equals(rec.getStatus()))
            {
                System.out.println(rec);
            }
            else if ("EMPTY_IP_ADDRESS".equals(rec.getStatus()))
            {
                System.out.println("IP address cannot be blank.");
            }
            else if ("INVALID_IP_ADDRESS".equals(rec.getStatus()))
            {
                System.out.println("Invalid IP address.");
            }
            else if ("MISSING_FILE".equals(rec.getStatus()))
            {
                System.out.println("Invalid database path.");
            }
            else if ("IPV6_NOT_SUPPORTED".equals(rec.getStatus()))
            {
                System.out.println("This BIN does not contain IPv6 data.");
            }
            else
            {
                System.out.println("Unknown error." + rec.getStatus());
            }
            System.out.println("Java Component: " + rec.getVersion());
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace(System.out);
        }
        finally
        {
            loc.Close();
        }
    }
}
