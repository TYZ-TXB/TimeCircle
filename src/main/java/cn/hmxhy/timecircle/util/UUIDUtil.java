package cn.hmxhy.timecircle.util;

public class UUIDUtil {

	public static String getUUID() {
		return getUUID(32);
	}

	public static String getUUID(int length) {
		String s = java.util.UUID.randomUUID().toString();
		s = s.replace("-", "");
		return s.substring(0, length);
	}


}
