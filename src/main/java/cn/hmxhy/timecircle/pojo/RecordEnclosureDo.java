package cn.hmxhy.timecircle.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Table(name = "record_enclosure")
@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class RecordEnclosureDo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "url")
	private String url;
	@Column(name = "file_describe")
	private String fileDescribe;
	@Column(name = "rid")
	private Long rid;
	@Column(name = "gmt_create")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date gmtCreate;
	@Column(name = "gmt_modified")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date gmtModified;
}
