package cn.hmxhy.timecircle.dao;

import cn.hmxhy.timecircle.pojo.RecordEnclosureDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordEnclosureDao extends JpaRepository<RecordEnclosureDo,Long> {
	List<RecordEnclosureDo> findAllByRid(Long rid);
}
