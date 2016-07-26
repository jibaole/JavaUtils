package com.comm.util.ip.test;
/* OrgLookupTest.java */

import java.io.IOException;

import com.comm.util.ip.LookupService;

/* sample of how to use the GeoIP Java API with GeoIP Organization and ISP databases */
/* This example can also be used with the GeoIP Domain and ASNum databases */
/* Usage: java OrgLookupTest 64.4.4.4 */

class ASNumLookupTestV6 {
    public static void main(String[] args) {
	try {
	    LookupService asnl = new LookupService("/usr/local/share/GeoIP/GeoIPASNumv6.dat");
	    System.out.println("ASNum V6: " + asnl.getOrgV6(args[0]));
	    asnl.close();
	}
	catch (IOException e) {
	    System.out.println("IO Exception");
	}
    }
}
