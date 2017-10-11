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

import com.mysql.jdbc.Blob;

@Entity
@Table(name="t_resource")
public class Resource {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="resourceName")
	private String resourceName;
	@Column(name="url")
	private String url;
	@Column(name="image")
	private String image;
	@Column(name="description")
	private String description;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
			name="t_role_res",
			joinColumns={
					@JoinColumn(name="res_id")
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
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Resource(int id, String resourceName, String url, String image, String description, Set<Role> roles) {
		super();
		this.id = id;
		this.resourceName = resourceName;
		this.url = url;
		this.image = image;
		this.description = description;
		this.roles = roles;
	}
	public Resource(String resourceName, String url, String image, String description) {
		super();
		this.resourceName = resourceName;
		this.url = url;
		this.image = image;
		this.description = description;
	}
	public Resource(int id) {
		super();
		this.id = id;
	}
	public Resource() {
		super();
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", resourceName=" + resourceName + ", url=" + url + ", image=" + image
				+ ", description=" + description + "]";
	}
}
