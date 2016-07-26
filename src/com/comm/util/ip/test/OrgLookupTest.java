package com.comm.util.ip.test;
/* OrgLookupTest.java */

import java.io.IOException;

import com.comm.util.ip.LookupService;

/* sample of how to use the GeoIP Java API with GeoIP Organization and ISP databases */
/* This example can also be used with the GeoIP Domain and ASNum databases */
/* Usage: java OrgLookupTest 64.4.4.4 */

class OrgLookupTest {
    public static void main(String[] args) {
	try {
	    LookupService orgl = new LookupService("/usr/local/share/GeoIP/GeoIPOrg.dat");
	    LookupService ispl = new LookupService("/usr/local/share/GeoIP/GeoIPISP.dat");
	    System.out.println("Organization: " + orgl.getOrg(args[0]) +
			       "\tISP: " + ispl.getOrg(args[0]));
	    orgl.close();
	    ispl.close();
	}
	catch (IOException e) {
	    System.out.println("IO Exception");
	}
    }
}
