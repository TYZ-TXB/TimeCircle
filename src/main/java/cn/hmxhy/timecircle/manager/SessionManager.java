package cn.hmxhy.timecircle.manager;

import cn.hmxhy.timecircle.pojo.UserDo;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	public static Map<String, Object> getUserInfoByUserDo(UserDo userDo) {
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("UID", userDo.getId());
		userInfo.put("head", userDo.getHeadPortrait());
		userInfo.put("name", userDo.getNickName());
		userInfo.put("status", userDo.getJurisdiction());
		return userInfo;
	}

	public static Long getUIDByUserInfo(Map<String, Object> userInfo) {
		return (Long) userInfo.get("UID");
	}
}
