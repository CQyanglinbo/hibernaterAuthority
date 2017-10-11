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
	 * �������ݿ⣬�����Ự������
	 */
	public void init() {
		// ���������ļ�
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		// ע���׼����
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		StandardServiceRegistry ssr = ssrb.applySettings(configuration.getProperties()).build();
		// ͨ����׼������������ļ����ûỰ����
		sessionFactory = configuration.buildSessionFactory(ssr);
		// ����һ���Ự
		session = sessionFactory.openSession();
		// ��������
		transaction = session.beginTransaction();
		System.out.println("���ӳɹ�");
	}

	/**
	 * �ر����ݿ⣬�ͷ���Դ
	 */
	public void close() {
		// �ر�
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("�رճɹ�");
	}

	public void save() {
		init();
		Resource resource = new Resource("�ջ�����", "http://hpeu.online.shixun.com", null, "�Ի�������ջ��Ǽǵ�");
		User user = new User("admin", "123456", "���ڸ��û�");
		Role role = new Role("����Ա", "��������Ȩ��", "���û����й���ԱȨ��");
		role.getResources().add(resource);
		role.getUsers().add(user);
		session.save(role);
		close();
	}

	/**
	 * ע���û������浽t_user����
	 * 
	 * @param loginName
	 * @param password
	 * @param status
	 */
	public void saveUser(String loginName, String password, String status) {
		init();
		User user = new User(loginName, password, status);
		// Ϊע���û���ӽ�ɫ
		Role role = new Role("��ͨ�û�", "ӵ��ɾ�Ĳ鹦��", "�Ѽ���");
		user.getRoles().add(role);
		session.save(user);
		close();
	}

	/**
	 * ��¼��֤�˻��������Ƿ��������ȷ
	 * 
	 * @param loginName
	 * @param password
	 * @return userId���ڵ���0��ʾ��������ȷ��-1��ʾ�����ڻ����˻����������
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
	 * �ȸ���������û���������鿴�Ƿ�������󣬼�����ȷ�����޸��û���
	 * @param loginName
	 * @param newLoginName
	 * @param password
	 */
	public void update_loginName(String loginName, String newLoginName,String password) {
		int userId=Query(loginName,password);
		if(userId>=0){
			init();
			User user=new User(userId,newLoginName,password,"�Ѽ���");
			session.update(user);
			close();
		}else{
			System.out.println("�˻������������");
		}	
	}
	/**
	 * �ȸ���������û���������鿴�Ƿ�������󣬼�����ȷ�����޸�����
	 * @param loginName
	 * @param password
	 * @param newPassword
	 */
	public void update_password(String loginName,String password,String newPassword){
		int userId=Query(loginName,password);
		if(userId>=0){
			init();
			User user=new User(userId,loginName,newPassword,"�Ѽ���");
			session.update(user);
			close();
		}else{
			System.out.println("�˻������������");
		}	
	}
	/**
	 * �ȸ���������û���������鿴�Ƿ�������󣬼�����ȷ�����޸ı�ע
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
			System.out.println("�˻������������");
		}	
	}
	/**
	 * ����userId��ѯ�û��Ľ�ɫ��Ϣ
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
	 * ���ݽ�ɫ����ѯ��Դ��Ϣ
	 * @param roleName
	 */
	public void QueryResource(String roleName){
		init();
		//����roleName��ѯroleId
		Query query=session.createQuery("from Role where roleName=?");
		query.setString(0, roleName);
		Iterator<Role> it=query.list().iterator();
		int roleId=-1;
		while(it.hasNext()){
			Role role=it.next();
			roleId=role.getId();
		}
		//��roleId��ѯresouce��Ϣ
		Role role1=(Role) session.get(Role.class, roleId);
		Iterator<Resource> it1=role1.getResources().iterator();
		while(it1.hasNext()){
			Resource res=it1.next();
			System.out.println(res.toString());
		}
		close();
	}
	/**
	 * ����û��б�
	 */
	public void QueryUser(){
		init();
		Query query=session.createQuery("from User");
		Iterator<User> it=query.list().iterator();
		System.out.println("�û��б�Ϊ:");
		while(it.hasNext()){
			User user=it.next();
			System.out.println(user.toString());
		}
		close();
	}
	/**
	 * �����ɫ�б�
	 */
	public void QueryRole(){
		init();
		Query query=session.createQuery("from Role");
		Iterator<Role> it=query.list().iterator();
		System.out.println("��ɫ�б�Ϊ:");
		while(it.hasNext()){
			Role role=it.next();
			System.out.println(role.toString());
		}
		close();
	}
	/**
	 * �����Դ�б�
	 */
	public void QueryResource(){
		init();
		Query query=session.createQuery("from Resource");
		Iterator<Resource> it=query.list().iterator();
		System.out.println("��Դ�б�Ϊ:");
		while(it.hasNext()){
			Resource res=it.next();
			System.out.println(res.toString());
		}
		close();
	}
	/**
	 * ��������û������Ľ�ɫ��Ϣ�Լ���Դ��Ϣ
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
	 * ����userId��ɾ���û�
	 * @param userId
	 */
	public void deleteUser(int userId){
		init();
		session.delete(new User(userId));
		close();
	}
	/**
	 * ����roleId��ɾ����ɫ
	 * @param roleId
	 */
	public void deleteRole(int roleId){
		init();
		session.delete(new Role(roleId));
		close();
	}
	/**
	 * ����resIdɾ����Դ��Ϣ
	 * @param resId
	 */
	public void deleteResource(int resId){
		init();
		session.delete(new Resource(resId));
		close();
	}
	/**
	 * ����resId�޸���Դ����
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
	 * ����resId�޸ı�ע
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
	 * ����roleId���޸�roleName
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
	 *  ����roleId���޸�Status
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
	 * ����roleId���޸�description
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
	 * ����userId���޸��û���״̬
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
