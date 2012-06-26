package it.rate.workload;

import java.math.BigInteger;
import java.util.Random;

public class RandomString {
	public static String nextEmail() {
		String randomString = new BigInteger(130, new Random()).toString(32);
		String mail = randomString.substring(0, 9);
		String domain = randomString.substring(10, 15) + "."
				+ randomString.toString().substring(16, 19);
		return mail + "@" + domain;
	}

	public static String nextUrl() {
		String randomString = new BigInteger(130, new Random()).toString(32);
		return "www." + randomString.substring(0, 12) + "."
				+ randomString.substring(13, 16);
	}

	public static String nextComment() {
		return new BigInteger(130, new Random()).toString(32);
	}
}
