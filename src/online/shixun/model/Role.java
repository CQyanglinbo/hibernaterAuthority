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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

@Entity
@Table(name="t_role")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="roleName")
	private String roleName;
	@Column(name="description")
	private String description;
	@Column(name="status")
	private String status;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			name="t_role_res",
			joinColumns={
					@JoinColumn(name="role_id")
			},
			inverseJoinColumns={
					@JoinColumn(name="res_id")
			}
			)
	private Set<Resource> resources=new HashSet<Resource>();
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			name="t_authorization",
			joinColumns={
					@JoinColumn(name="role_id")
					
			},
			inverseJoinColumns={
					@JoinColumn(name="user_id")
			}
			)
	private Set<User> users=new HashSet<User>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Role() {
		super();
	}
	public Role(int id, String roleName, String description, String status, Set<Resource> resources, Set<User> users) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
		this.status = status;
		this.resources = resources;
		this.users = users;
	}
	public Role(String roleName, String description, String status) {
		super();
		this.roleName = roleName;
		this.description = description;
		this.status = status;
	}
	public Role(int id) {
		super();
		this.id = id;
	}
	public Role(String roleName, String description, String status, Set<Resource> resources, Set<User> users) {
		super();
		this.roleName = roleName;
		this.description = description;
		this.status = status;
		this.resources = resources;
		this.users = users;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", description=" + description + ", status=" + status
				+ "]";
	}
	
	public String toStringAndUserResource() {
		return "[" +users+", resource=" +resources+"]";
	}
	
}
