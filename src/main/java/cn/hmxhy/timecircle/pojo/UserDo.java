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
@Table(name = "users")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserDo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickName;
	/**
	 * 头像
	 * 默认为 headPortrait/defaultHeadPortrait.jpg
	 */
	@Column(name = "head_portrait")
	private String headPortrait = "headPortrait/defaultHeadPortrait.jpg";
	/**
	 * 性别
	 * 0 男
	 * 1 女
	 * 2 未知	默认值
	 */
	@Column(name = "sex")
	private Byte sex = 2;
	/**
	 * 年龄
	 * 默认 0
	 */
	@Column(name = "age")
	private Byte age = 0;
	/**
	 * 生日
	 * 默认当前时间
	 */
	@Column(name = "birthday")
	private Date birthday = new Date();
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 实名
	 * 0 未实名 默认
	 * 1 已实名
	 */
	@Column(name = "is_real_name")
	private Byte realName = 0;
	/**
	 * 身份证
	 */
	@Column(name = "id_card")
	private String idCard;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 权限
	 * 0 超级管理员
	 * 1 管理员
	 * 2 普通用户	默认值
	 */
	@Column(name = "jurisdiction")
	private Byte jurisdiction = 2;

	/**
	 * vip等级
	 * 默认为 0
	 */
	@Column(name = "vip")
	private Byte vip = 0;

	enum UserStatusEnum {
		DELETE((byte) 0), BLACKLIST((byte) 1), PROHIBIT((byte) 2), NORMAL((byte) 3);
		private final Byte status;

		UserStatusEnum(Byte status) {
			this.status = status;
		}

		public Byte getStatus() {
			return status;
		}
	}

	/**
	 * 用户状态
	 * 0 删除
	 * 1 黑名单
	 * 2 禁用
	 * 3 正常
	 */
	@Column(name = "status")
	private Byte status = UserStatusEnum.NORMAL.getStatus();
	/**
	 * 创建时间
	 */
	@Column(name = "gmt_create")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date gmtCreate;
	/**
	 * 更新时间
	 */
	@Column(name = "gmt_modified")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date gmtModified;
}
