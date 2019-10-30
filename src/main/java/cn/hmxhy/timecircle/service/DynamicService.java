package cn.hmxhy.timecircle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DynamicService {
	/**
	 * 发布动态 支持发布和隐藏两种状态
	 *
	 * @param content 内容
	 * @param status  状态 1为发布 2为隐藏
	 * @param images  图片列表
	 * @param session 会话session
	 * @return 是否成功增加
	 */
	JSONArray addDynamic(String content, Byte status, List<Map<String, String>> images, HttpSession session);

	/**
	 * 更新动态状态
	 * @param status	状态
	 * @param rid		动态id
	 * @return
	 */
	Byte updateStatus(Byte status,Long rid);

	/**
	 * 查找所有发布中的动态内容
	 *
	 * @param session 会话session
	 * @return 封装json
	 */
	JSON findAllDynamic(HttpSession session);

	/**
	 * 查找自己的所有发布动态
	 *
	 * @param session 会话session
	 * @return 返回封装session
	 */
	JSON findMyDynamic(HttpSession session);
}
