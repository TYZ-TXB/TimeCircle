package cn.hmxhy.timecircle.service;

import cn.hmxhy.timecircle.pojo.UserDo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

	UserDo userLogin(String email, String password, HttpServletRequest request);
	UserDo findOtherUserInfo(Long uid);

	/**
	 * 增加用户
	 * 主要根据邮箱增加，其他数据暂不考虑
	 *
	 * @param userDo 用户对象
	 * @return 布尔值
	 */
	UserDo addUser(UserDo userDo);

	/**
	 * 删除用户
	 *
	 * @param id 用户id
	 * @return
	 */
	Boolean delUser(Long id);

	/**
	 * 修改用户
	 *
	 * @param userDo 用户对象
	 * @return
	 */
	Boolean updateUser(UserDo userDo);
}
