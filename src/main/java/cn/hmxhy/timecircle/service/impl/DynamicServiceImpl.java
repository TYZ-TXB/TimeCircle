package cn.hmxhy.timecircle.service.impl;

import cn.hmxhy.timecircle.dao.RecordDao;
import cn.hmxhy.timecircle.dao.RecordEnclosureDao;
import cn.hmxhy.timecircle.dao.UserDao;
import cn.hmxhy.timecircle.manager.FilePathManager;
import cn.hmxhy.timecircle.manager.ReturnJSONManager;
import cn.hmxhy.timecircle.manager.SessionManager;
import cn.hmxhy.timecircle.pojo.RecordDo;
import cn.hmxhy.timecircle.pojo.RecordEnclosureDo;
import cn.hmxhy.timecircle.pojo.UserDo;
import cn.hmxhy.timecircle.service.DynamicService;
import cn.hmxhy.timecircle.util.FileUtil;
import cn.hmxhy.timecircle.util.UUIDUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DynamicServiceImpl implements DynamicService {
	@Autowired
	private RecordDao recordDao;
	@Autowired
	private RecordEnclosureDao recordEnclosureDao;
	@Autowired
	private UserDao userDao;

	@Override
	public JSONArray addDynamic(String content, Byte status, List<Map<String, String>> images, HttpSession session) {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("userInfo");
		RecordDo recordDo = new RecordDo();
		recordDo.setContent(content);
		recordDo.setStatus(status);
		recordDo.setUid(SessionManager.getUIDByUserInfo(userInfo));
		recordDo = recordDao.save(recordDo);
		if (recordDo != null) {
			List<String> paths = saveDynamicFile(images, recordDo.getUid());
			if (paths.size() > 0) {
				Long rid = recordDo.getId();
				for (String path : paths) {
					RecordEnclosureDo recordEnclosureDo = new RecordEnclosureDo();
					recordEnclosureDo.setUrl(path);
					recordEnclosureDo.setRid(rid);
					System.out.println(recordEnclosureDo);
					recordEnclosureDao.save(recordEnclosureDo);
				}
				List<RecordDo> recordDos = new ArrayList<>();
				recordDos.add(recordDo);
				return mergeData(recordDos, recordDo.getUid());
			}
		}
		return null;
	}

	/**
	 * @param status 状态
	 * @param rid    动态id
	 * @return
	 */
	@Override
	public Byte updateStatus(Byte status, Long rid) {
		recordDao.updateStatusById(rid, status);
		return null;
	}

	/**
	 * @param session 会话session
	 * @return
	 */
	@Override
	public JSON findAllDynamic(HttpSession session) {
		Long uid = SessionManager.getUIDByUserInfo((Map<String, Object>) session.getAttribute("userInfo"));
		List<Byte> status1 = new ArrayList<>();
		status1.add((byte) 1);
		List<Byte> status2 = new ArrayList<>();
		status2.add((byte) 1);
		status2.add((byte) 2);
		List<RecordDo> recordDos = recordDao.findAllByStatusInOrUidAndStatusInOrderByGmtModifiedAsc(status1, uid, status2);
		JSONArray jsonData = mergeData(recordDos, uid);
		return ReturnJSONManager.getSuccessStatus(ReturnJSONManager.SUCCESS, jsonData);
	}

	/**
	 * @param session 会话session
	 * @return
	 */
	@Override
	public JSON findMyDynamic(HttpSession session) {
		Long uid = SessionManager.getUIDByUserInfo((Map<String, Object>) session.getAttribute("userInfo"));
		List<Byte> status = new ArrayList<>();
		status.add((byte) 1);
		status.add((byte) 2);
		List<RecordDo> recordDos = recordDao.findAllByUidAndStatusInOrderByGmtModifiedAsc(uid, status);
		JSONArray jsonData = mergeData(recordDos, uid);
		System.out.println(jsonData);
		return ReturnJSONManager.getSuccessStatus(ReturnJSONManager.SUCCESS, jsonData);
	}

	/**
	 * 查询记录附件内容，并封装成动态数据
	 *
	 * @param recordDos 记录数据列表
	 * @return 动态封装json数组数据
	 */
	private JSONArray mergeData(List<RecordDo> recordDos, Long uid) {
		JSONArray jsonData = new JSONArray();
		for (RecordDo recordDo : recordDos) {
			System.out.println(recordDo);
			List<RecordEnclosureDo> recordEnclosureDos = recordEnclosureDao.findAllByRid(recordDo.getId());
			Optional<UserDo> userDoOptional = userDao.findById(recordDo.getUid());
			JSONObject jsonObject = null;
			if (userDoOptional.isPresent()) {
				jsonObject = ReturnJSONManager.getDynamic(recordDo, recordEnclosureDos, userDoOptional.get(), uid.equals(recordDo.getUid()));
			}
			jsonData.add(jsonObject);
		}
		return jsonData;
	}

	/**
	 * 保存动态附件
	 *
	 * @param images 附件内容
	 * @param uid    用户id
	 * @return
	 */
	private List<String> saveDynamicFile(List<Map<String, String>> images, Long uid) {
		List<String> paths = new ArrayList<>();
		String path = FilePathManager.getUserDynamicFilePath(uid);
		System.out.println(path);
		File upload = new File(FilePathManager.getProjectStaticAbsolutePath(), path);
		System.out.println(upload.getAbsoluteFile());
		if (!upload.exists()) {
			upload.mkdirs();
		}
		for (Map<String, String> image : images) {
			String localPath = path + UUIDUtil.getUUID() + "." + image.get("type");
			FileUtil.GenerateImage(image.get("imgBase64"), FilePathManager.getProjectStaticAbsolutePath() + localPath);
			paths.add(localPath);
		}
		return paths;
	}
}
