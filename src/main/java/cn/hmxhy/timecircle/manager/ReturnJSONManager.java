package cn.hmxhy.timecircle.manager;

import cn.hmxhy.timecircle.pojo.RecordDo;
import cn.hmxhy.timecircle.pojo.RecordEnclosureDo;
import cn.hmxhy.timecircle.pojo.UserDo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ReturnJSONManager {
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public static JSONObject getUserInfoJSON(UserDo userDo) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("uid", userDo.getId());
		jsonObject.put("nickName", userDo.getNickName());
		jsonObject.put("headImage", userDo.getHeadPortrait());
		jsonObject.put("sex", userDo.getSex());
		jsonObject.put("age", userDo.getAge());
		jsonObject.put("email", userDo.getEmail());
		jsonObject.put("phone", userDo.getPhone());
		return jsonObject;
	}

	/**
	 * 一条动态封装数据
	 *
	 * @param recordDo           动态消息
	 * @param recordEnclosureDos 动态附件列表
	 * @param userDo             动态发表人
	 * @return 封装json数据
	 */
	public static JSONObject getDynamic(RecordDo recordDo, List<RecordEnclosureDo> recordEnclosureDos, UserDo userDo, boolean isMe) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", recordDo.getId());
		jsonObject.put("head", userDo.getHeadPortrait());
		jsonObject.put("nickName", userDo.getNickName());
		jsonObject.put("uid", recordDo.getUid());
		jsonObject.put("title", recordDo.getTitle());
		jsonObject.put("content", recordDo.getContent());
		jsonObject.put("creatTime", recordDo.getGmtCreate());
		jsonObject.put("modifiedTime", recordDo.getGmtModified());
		jsonObject.put("status", recordDo.getStatus());
		jsonObject.put("isMe", isMe);
		JSONArray jsonArray = new JSONArray();
		for (RecordEnclosureDo recordEnclosureDo : recordEnclosureDos) {
			JSONObject localJSONObject = new JSONObject();
			localJSONObject.put("id", recordEnclosureDo.getId());
			localJSONObject.put("url", recordEnclosureDo.getUrl());
			localJSONObject.put("creatTime", recordEnclosureDo.getGmtCreate());
			localJSONObject.put("modifiedTime", recordEnclosureDo.getGmtModified());
			jsonArray.add(localJSONObject);
		}
		jsonObject.put("imgs", jsonArray);
		return jsonObject;
	}

	public static JSON getSuccessStatus() {
		return getSuccessStatus("");
	}

	public static JSON getSuccessStatus(String msg) {
		return getSuccessStatus(msg, new JSONObject());
	}

	public static JSON getSuccessStatus(String msg, JSON data) {
		return getJSON(SUCCESS, msg, data);
	}

	public static JSON getErrorStatus() {
		return getErrorStatus("");
	}

	public static JSON getErrorStatus(String msg) {
		return getErrorStatus(msg, new JSONObject());
	}

	public static JSON getErrorStatus(String msg, JSON data) {
		return getJSON(ERROR, msg, data);
	}

	private static JSON getJSON(String status, String msg, JSON data) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("msg", msg);
		json.put("data", data);
		return json;
	}
}
