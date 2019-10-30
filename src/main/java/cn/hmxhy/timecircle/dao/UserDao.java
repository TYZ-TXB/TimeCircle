package cn.hmxhy.timecircle.dao;

import cn.hmxhy.timecircle.pojo.UserDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<UserDo, Long> {
	List<UserDo> findAllByEmailAndPasswordAndStatus(String email, String password, Byte status);

	UserDo findByEmailAndPasswordAndStatus(String email, String password, Byte status);

	List<UserDo> findAllByEmail(String email);

	List<UserDo> findAllByEmailAndStatusNot(String email, Byte status);

	List<UserDo> findAllByEmailAndStatusNotIn(String email, List<Byte> status);

	List<UserDo> findAllByEmailAndStatusIn(String email, List<Byte> status);
}
