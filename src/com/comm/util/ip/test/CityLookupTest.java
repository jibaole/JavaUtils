package com.comm.util.ip.test;
/* CityLookupTest.java */

import java.io.IOException;

import com.comm.util.ip.Location;
import com.comm.util.ip.LookupService;

/* sample of how to use the GeoIP Java API with GeoIP City database */
/* Usage: java CityLookupTest 64.4.4.4 */

class CityLookupTest {
    public static void main(String[] args) {
	try {
	    LookupService cl = new LookupService("/root/software/GeoIP/src/GeoLiteCity.dat",
					LookupService.GEOIP_MEMORY_CACHE );
            Location l1 = cl.getLocation("213.52.50.8");
         //   Location l2 = cl.getLocation(args[0]);
	    System.out.println("countryCode: " + l1.countryCode +
                               "\n countryName: " + l1.countryName +
                               "\n region: " + l1.region +
                            //   "\n regionName: " + RegionName.regionNameByCode(l2.countryCode, l2.region) +
                               "\n city: " + l1.city +
                               "\n postalCode: " + l1.postalCode +
                               "\n latitude: " + l1.latitude +
                               "\n longitude: " + l1.longitude +
                           //    "\n distance: " + l1.distance(l1) +
                           //    "\n distance: " + l1.distance(l2) + 
 			       "\n metro code: " + l1.metro_code +
 			       "\n area code: " + l1.area_code +""
                         //      "\n timezone: " + timeZone.timeZoneByCountryAndRegion(l2.countryCode, l2.region)
 			       );

	    cl.close();
	}
	catch (IOException e) {
	    System.out.println("IO Exception");
	}
    }
}
