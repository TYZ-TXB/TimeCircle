package cn.hmxhy.timecircle.controller;

import cn.hmxhy.timecircle.manager.ReturnJSONManager;
import cn.hmxhy.timecircle.service.DynamicService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DynamicController {

	@Autowired
	private DynamicService dynamicService;

	@RequestMapping("/toMyDynamic")
	public String toMyDynamic() {
		return "myDynamic";
	}


	@RequestMapping("/toRelease")
	public String toRelease() {
		return "release";
	}

	@RequestMapping("/addDynamic")
	@ResponseBody
	public JSON addDynamic(@RequestBody String JSONData, HttpServletRequest request) {
		JSONObject jsonObject = JSONObject.parseObject(JSONData);
		JSONArray jsonArray = jsonObject.getJSONArray("enclosure");
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String, String> map = new HashMap<>();
			JSONObject object = jsonArray.getJSONObject(i);
			String base64 = object.getString("imgBase64");
			base64 = base64.substring(base64.indexOf("base64,") + 7);
			map.put("imgBase64", base64);
			map.put("type", object.getString("type"));
			list.add(map);
		}
		System.out.println(jsonObject.getString("content"));
		JSONArray dynamicArray = dynamicService.addDynamic(jsonObject.getString("content"), jsonObject.getByte("status"), list, request.getSession());
		if(dynamicArray != null){
			return ReturnJSONManager.getSuccessStatus("",dynamicArray);
		}
		return ReturnJSONManager.getErrorStatus();
	}

	@RequestMapping("/getAllDynamic")
	@ResponseBody
	public JSON getAllDynamic(HttpServletRequest request) {
		return dynamicService.findAllDynamic(request.getSession());
	}

	@RequestMapping("/getMyDynamic")
	@ResponseBody
	public JSON getMyDynamic(HttpServletRequest request) {
		return dynamicService.findMyDynamic(request.getSession());
	}

	@RequestMapping("/updateDynamicStatus")
	@ResponseBody
	public JSON updateDynamicStatus(Long rid, Byte status) {
		System.out.println(rid + ":" + status);
		dynamicService.updateStatus(status, rid);
		return ReturnJSONManager.getSuccessStatus();
	}
}