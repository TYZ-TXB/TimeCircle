package cn.hmxhy.timecircle.dao;

import cn.hmxhy.timecircle.pojo.UserAuthDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthDao extends JpaRepository<UserAuthDo,Long> {
}
