package cn.hmxhy.timecircle.manager;

import cn.hmxhy.timecircle.util.FileUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class FilePathManager {
	/**
	 * 用户文件保存目录
	 */
	private static final String USER_FILE_PATH = "users" + File.separator;
	/**
	 * 用户动态文件保存目录
	 */
	private static final String USER_DYNAMIC_FILE_PATH = "dynamic" + File.separator;
	/**
	 * 文件缓存目录
	 */
	private static final String FileCachePath = "static" + File.separator + "cache" + File.separator;

	/**
	 * 获取项目绝对路径
	 *
	 * @return
	 */
	public static String getProjectAbsolutePath() {
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return path.getAbsolutePath() + File.separator;
	}

	/**
	 * 获取项目静态资源绝对路径
	 *
	 * @return
	 */
	public static String getProjectStaticAbsolutePath() {
		return getProjectAbsolutePath() + "static" + File.separator;
	}

	/**
	 * 获取用户动态文件保存目录
	 *
	 * @param uid 用户id
	 * @return 返回目录地址
	 */
	public static String getUserDynamicFilePath(Long uid) {
		return USER_FILE_PATH + uid + File.separator + USER_DYNAMIC_FILE_PATH;
	}

}
