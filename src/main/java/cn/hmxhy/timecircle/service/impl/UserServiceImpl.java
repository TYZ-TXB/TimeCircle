package cn.hmxhy.timecircle.service.impl;

import cn.hmxhy.timecircle.dao.UserDao;
import cn.hmxhy.timecircle.manager.SessionManager;
import cn.hmxhy.timecircle.pojo.UserDo;
import cn.hmxhy.timecircle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDo userLogin(String email, String password, HttpServletRequest request) {
		UserDo userDo = userDao.findByEmailAndPasswordAndStatus(email, password, (byte) 3);
		if (userDo == null) {
			return null;
		}
		System.out.println(userDo);
		HttpSession session = request.getSession();
		session.setAttribute("userInfo", SessionManager.getUserInfoByUserDo(userDo));
		return userDo;
	}

	@Override
	public UserDo findOtherUserInfo(Long uid) {
		Optional<UserDo> userDoOptional = userDao.findById(uid);
		if (userDoOptional.isPresent()) {
			return userDoOptional.get();
		}
		return null;
	}

	@Override
	public UserDo addUser(UserDo userDo) {
		List<UserDo> userDos = userDao.findAllByEmailAndStatusNot(userDo.getEmail(), (byte) 0);
		if (userDos != null && userDos.size() > 0) {
			return null;
		}
		userDo = userDao.save(userDo);
		return userDo;
	}

	@Override
	public Boolean delUser(Long id) {
		return null;
	}

	@Override
	public Boolean updateUser(UserDo user) {
		return null;
	}
}
