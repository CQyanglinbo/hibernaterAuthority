package online.shixun.test;

import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;

import online.shixun.model.Resource;
import online.shixun.model.Role;
import online.shixun.model.User;

public class HibernateAuthhorityTest {
	SessionFactory sessionFactory = null;
	Session session = null;
	Transaction transaction = null;

	/**
	 * 连接数据库，开启会话、事务
	 */
	public void init() {
		// 加载配置文件
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		// 注册标准服务
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		StandardServiceRegistry ssr = ssrb.applySettings(configuration.getProperties()).build();
		// 通过标准服务加载配置文件后获得会话工厂
		sessionFactory = configuration.buildSessionFactory(ssr);
		// 开启一个会话
		session = sessionFactory.openSession();
		// 开启事务
		transaction = session.beginTransaction();
		System.out.println("连接成功");
	}

	/**
	 * 关闭数据库，释放资源
	 */
	public void close() {
		// 关闭
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("关闭成功");
	}

	public void save() {
		init();
		Resource resource = new Resource("收货管理", "http://hpeu.online.shixun.com", null, "对货物进行收货登记等");
		User user = new User("admin", "123456", "存在该用户");
		Role role = new Role("管理员", "具有所有权限", "该用户具有管理员权限");
		role.getResources().add(resource);
		role.getUsers().add(user);
		session.save(role);
		close();
	}

	/**
	 * 注册用户并保存到t_user表中
	 * 
	 * @param loginName
	 * @param password
	 * @param status
	 */
	public void saveUser(String loginName, String password, String status) {
		init();
		User user = new User(loginName, password, status);
		// 为注册用户添加角色
		Role role = new Role("普通用户", "拥有删改查功能", "已激活");
		user.getRoles().add(role);
		session.save(user);
		close();
	}

	/**
	 * 登录验证账户和密码是否存在且正确
	 * 
	 * @param loginName
	 * @param password
	 * @return userId大于等于0表示存在且正确，-1表示不存在或者账户或密码错误
	 */
	public int Query(String loginName, String password) {
		init();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("loginName", loginName));
		criteria.add(Restrictions.eq("password", password));
		Iterator<User> it = criteria.list().iterator();
		while (it.hasNext()) {
			User user = it.next();
			int userId = user.getId();
			close();
			return userId;
		}
		close();
		return -1;
	}
	/**
	 * 先根据输入的用户名和密码查看是否输入错误，检验正确后再修改用户名
	 * @param loginName
	 * @param newLoginName
	 * @param password
	 */
	public void update_loginName(String loginName, String newLoginName,String password) {
		int userId=Query(loginName,password);
		if(userId>=0){
			init();
			User user=new User(userId,newLoginName,password,"已激活");
			session.update(user);
			close();
		}else{
			System.out.println("账户名或密码错误");
		}	
	}
	/**
	 * 先根据输入的用户名和密码查看是否输入错误，检验正确后再修改密码
	 * @param loginName
	 * @param password
	 * @param newPassword
	 */
	public void update_password(String loginName,String password,String newPassword){
		int userId=Query(loginName,password);
		if(userId>=0){
			init();
			User user=new User(userId,loginName,newPassword,"已激活");
			session.update(user);
			close();
		}else{
			System.out.println("账户名或密码错误");
		}	
	}
	/**
	 * 先根据输入的用户名和密码查看是否输入错误，检验正确后再修改备注
	 * @param loginName
	 * @param password
	 * @param status
	 */
	public void update_status(String loginName,String password,String status){
		int userId=Query(loginName,password);
		if(userId>=0){
			init();
			User user=new User(userId,loginName,password,status);
			session.update(user);
			close();
		}else{
			System.out.println("账户名或密码错误");
		}	
	}
	/**
	 * 根据userId查询用户的角色信息
	 * @param userId
	 */
	public void QueryRole(int userId) {
		init();
		User user=(User) session.get(User.class, userId);
		Iterator<Role> it=user.getRoles().iterator();
		while(it.hasNext()){
			System.out.println("hello");
			Role role=it.next();
			System.out.println(role.toString());	
		}
		close();
	}
	/**
	 * 根据角色名查询资源信息
	 * @param roleName
	 */
	public void QueryResource(String roleName){
		init();
		//根据roleName查询roleId
		Query query=session.createQuery("from Role where roleName=?");
		query.setString(0, roleName);
		Iterator<Role> it=query.list().iterator();
		int roleId=-1;
		while(it.hasNext()){
			Role role=it.next();
			roleId=role.getId();
		}
		//由roleId查询resouce信息
		Role role1=(Role) session.get(Role.class, roleId);
		Iterator<Resource> it1=role1.getResources().iterator();
		while(it1.hasNext()){
			Resource res=it1.next();
			System.out.println(res.toString());
		}
		close();
	}
	/**
	 * 浏览用户列表
	 */
	public void QueryUser(){
		init();
		Query query=session.createQuery("from User");
		Iterator<User> it=query.list().iterator();
		System.out.println("用户列表为:");
		while(it.hasNext()){
			User user=it.next();
			System.out.println(user.toString());
		}
		close();
	}
	/**
	 * 浏览角色列表
	 */
	public void QueryRole(){
		init();
		Query query=session.createQuery("from Role");
		Iterator<Role> it=query.list().iterator();
		System.out.println("角色列表为:");
		while(it.hasNext()){
			Role role=it.next();
			System.out.println(role.toString());
		}
		close();
	}
	/**
	 * 浏览资源列表
	 */
	public void QueryResource(){
		init();
		Query query=session.createQuery("from Resource");
		Iterator<Resource> it=query.list().iterator();
		System.out.println("资源列表为:");
		while(it.hasNext()){
			Resource res=it.next();
			System.out.println(res.toString());
		}
		close();
	}
	/**
	 * 浏览所有用户关联的角色信息以及资源信息
	 */
	public void allTable(){
		init();
		Query query=session.createQuery("from Role");
		Iterator<Role> it=query.list().iterator();
		while(it.hasNext()){
			Role role=it.next();
			role.getUsers();
			role.getResources();
			System.out.println(role.toStringAndUserResource());
		}
		close();
	}
	/**
	 * 根据userId来删除用户
	 * @param userId
	 */
	public void deleteUser(int userId){
		init();
		session.delete(new User(userId));
		close();
	}
	/**
	 * 根据roleId来删除角色
	 * @param roleId
	 */
	public void deleteRole(int roleId){
		init();
		session.delete(new Role(roleId));
		close();
	}
	/**
	 * 根据resId删除资源信息
	 * @param resId
	 */
	public void deleteResource(int resId){
		init();
		session.delete(new Resource(resId));
		close();
	}
	/**
	 * 根据resId修改资源名称
	 * @param resId
	 * @param resourName
	 */
	public void modifyResourceName(int resId,String resourName){
		init();
		Resource res=(Resource) session.get(Resource.class, resId);
		res.setResourceName(resourName);
		close();
	}
	/**
	 * 根据resId修改备注
	 * @param resId
	 * @param decription
	 */
	public void modifyResourceDescription(int resId,String decription){
		init();
		Resource res=(Resource) session.get(Resource.class, resId);
		res.setDescription(decription);
		close();
	}
	/**
	 * 根据roleId来修改roleName
	 * @param roleId
	 * @param roleName
	 */
	public void modifyRoleName(int roleId,String roleName){
		init();
		Role role=(Role) session.get(Role.class, roleId);
		role.setRoleName(roleName);
		close();
	}
	/**
	 *  根据roleId来修改Status
	 * @param roleId
	 * @param Status
	 */
	public void modifyRoleStatus(int roleId,String Status){
		init();
		Role role=(Role) session.get(Role.class, roleId);
		role.setStatus(Status);
		close();
	}
	/**
	 * 根据roleId来修改description
	 * @param roleId
	 * @param description
	 */
	public void modifyRoleDescription(int roleId,String description){
		init();
		Role role=(Role) session.get(Role.class, roleId);
		role.setDescription(description);
		close();
	}
	/**
	 * 根据userId来修改用户的状态
	 * @param userId
	 * @param status
	 */
	public void modifyUserStatus(int userId,String status){
		init();
		User user=(User) session.get(User.class, userId);
		user.setStatus(status);
		close();
	}
}
