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
@Entity
@Table(name = "user_auths")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserAuthDo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "identity_type")
	private String identityType;
	@Column(name = "identifier")
	private String identifier;
	@Column(name = "uid")
	private Long uid;
	@Column(name = "gmt_create")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date gmtCreat;
	@Column(name = "gmt_modified")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date gmtModified;
}
