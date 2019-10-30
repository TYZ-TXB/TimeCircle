package cn.hmxhy.timecircle.dao;

import cn.hmxhy.timecircle.pojo.RecordDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecordDao extends JpaRepository<RecordDo, Long> {
	List<RecordDo> findAllByUidAndStatusInOrderByGmtModifiedAsc(Long uid, List<Byte> status);

	List<RecordDo> findAllByStatusInOrUidAndStatusInOrderByGmtModifiedAsc(List<Byte> status1, Long uid, List<Byte> status2);

	@Modifying
	@Transactional
	@Query("update RecordDo set status = ?2 where id = ?1")
	int updateStatusById(Long id, Byte status);
}
