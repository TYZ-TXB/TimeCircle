package cn.hmxhy.timecircle.dao;

import cn.hmxhy.timecircle.pojo.FutureTaskDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutureTaskDao extends JpaRepository<FutureTaskDo,Long> {
}
