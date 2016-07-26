package com.comm.util.ip;

import java.util.HashMap;
import java.util.Map;

public class IpUtil {

	private static String PREFIX_FILE_DAT = "/opt/GeoIP/";

	public static Location getLocationByIpAddress(String ipAddress) {

		Map map = new HashMap();
		Location l1 = null;

		try {

			LookupService cl = new LookupService(IpUtil.PREFIX_FILE_DAT
					+ "GeoLiteCity.dat",
			// "/root/software/GeoIP/src/GeoIP.dat",
					LookupService.GEOIP_MEMORY_CACHE);

			l1 = cl.getLocation(ipAddress);

			// System.out.println("ll=" + l1.countryName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l1;
	}

	public static Country getCountryByIpAddress(String ipAddress) {

		Country l1 = null;

		try {

			LookupService cl = new LookupService(IpUtil.PREFIX_FILE_DAT
					+ "GeoIP.dat", LookupService.GEOIP_MEMORY_CACHE);

			l1 = cl.getCountry(ipAddress);

			// System.out.println("ll=" + l1.countryName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l1;
	}

	public static String getOrgByIpAddress(String ipAddress) {

		String l1 = null;

		try {

			LookupService cl = new LookupService(IpUtil.PREFIX_FILE_DAT
					+ "GeoLiteCity.dat", LookupService.GEOIP_MEMORY_CACHE);

			l1 = cl.getOrg(ipAddress);
			// System.out.println("ll=" + l1.countryName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l1;
	}

	public static String getTimeZoneByIpAddress(String ipAddress) {

		String l1 = null;

		try {

			Country country = IpUtil.getCountryByIpAddress(ipAddress);

			Location location = IpUtil.getLocationByIpAddress(ipAddress);

			l1 = TimeZone.timeZoneByCountryAndRegion(country.getCode(),
					location.region);

			// System.out.println("ll=" + l1.countryName);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return l1;
	}

	public static void main(String args[]) {

		String ipAddress = "123.57.5.210";// "213.52.50.8";

		Location location = IpUtil.getLocationByIpAddress(ipAddress);

		System.out.println("location.city=" + location.city);
		System.out.println("location.region=" + location.region);
		System.out.println("location.area_code=" + location.area_code);
		System.out.println("location.postalCode=" + location.postalCode);
		System.out.println("location.countryCode=" + location.countryCode);
		System.out.println("location.countryName=" + location.countryName);
		
		
		Country country = IpUtil.getCountryByIpAddress(ipAddress);

		System.out.println("country.name=" + country.getName());

		String org = IpUtil.getOrgByIpAddress(ipAddress);

		System.out.println("org=" + org);

		String TimeZone = IpUtil.getTimeZoneByIpAddress(ipAddress);

		System.out.println("TimeZone=" + TimeZone);

	}

}
