package com.comm.util.ip.test;
/* RegionLookupTest.java */

/* Requires subscription to MaxMind GeoIP Region database */

import java.io.IOException;

import com.comm.util.ip.LookupService;
import com.comm.util.ip.Region;

class RegionLookupTest {
    public static void main(String[] args) {
        try {
            LookupService cl = new LookupService("/usr/local/share/GeoIP/GeoIPRegion.dat");
            Region l = cl.getRegion(args[0]);
            System.out.println("Country Code: " + l.countryCode);
            System.out.println("Country Name: " + l.countryName);
            System.out.println("Region Code: " + l.region);
        //    System.out.println("Region Name: " + regionName.regionNameByCode(l.countryCode,l.region));
            cl.close();
        }
        catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}
