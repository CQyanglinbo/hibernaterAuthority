package online.shixun.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="loginName")
	private String loginName;
	@Column(name="password")
	private String password;
	@Column(name="status")
	private String status;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			name="t_authorization",
			joinColumns={
					@JoinColumn(name="user_id")
			},
			inverseJoinColumns={
					@JoinColumn(name="role_id")
			}
			)
	private Set<Role> roles=new HashSet<Role>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public User() {
		super();
	}
	public User(int id, String loginName, String password, String status, Set<Role> roles) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.status = status;
		this.roles = roles;
	}
	public User(int id) {
		super();
		this.id = id;
	}
	public User(String loginName, String password, String status) {
		super();
		this.loginName = loginName;
		this.password = password;
		this.status = status;
	}
	public User(int id, String loginName, String password, String status) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.status = status;
	}
	
	public User(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", password=" + password + ", status=" + status
				+ ", roles=" + roles + "]";
	}
	
	public String toStringNotrole() {
		return "User [id=" + id + ", loginName=" + loginName + ", password=" + password + ", status=" + status
				+ "]";
	}
}
