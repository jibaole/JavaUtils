package com.comm.util.ip.test;
/* OrgLookupTest.java */

import java.io.IOException;

import com.comm.util.ip.LookupService;

/* sample of how to use the GeoIP Java API with GeoIP Organization and ISP databases */
/* This example can also be used with the GeoIP Domain and ASNum databases */
/* Usage: java OrgLookupTest 64.4.4.4 */

class ASNumLookupTest {
    public static void main(String[] args) {
	try {
	    LookupService asnl = new LookupService("/root/software/GeoIP/src/GeoIP.dat");
	    System.out.println("ASNum: " + asnl.getOrg(args[0]));
	    asnl.close();
	}
	catch (IOException e) {
	    System.out.println("IO Exception");
	}
    }
}
