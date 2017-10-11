package online.shixun.service;

import java.util.Scanner;

import online.shixun.test.HibernateAuthhorityTest;

public class operatorMethod {
	int userId=-1;
	HibernateAuthhorityTest ht=new HibernateAuthhorityTest();
	Scanner in=new Scanner(System.in);
	/**
	 * ���˵�ҳ��
	 */
	public void menu(){
		
		while(true){
			System.out.println("-----------------��¼����-----------------");
			System.out.println("1.��¼  2.ע��  3.�˳�");
			System.out.println("������1/2/3");
			int choice=in.nextInt();
			if(choice==1){
				userId=login();
				if(userId!=-1){
					loginSuccessPage();
				}
			}else if(choice==2){
				register();
			}else if(choice==3){
				System.exit(0);
			}else{
				System.out.println("�����������������");
			}
		}
	}
	/**
	 * ע���û�
	 */
	public void register(){
		System.out.println("������������ע����û���:");
		String loginName=in.next();
		System.out.println("����:");
		String password=in.next();
		System.out.println("��ע:");
		String status=in.next();
		HibernateAuthhorityTest hat=new HibernateAuthhorityTest();
		hat.saveUser(loginName, password, status);
	}
	/**
	 * ��¼��֤
	 */
	public int login(){
		System.out.println("�������û���:");
		String loginName=in.next();
		System.out.println("����:");
		String password=in.next();
		HibernateAuthhorityTest hat=new HibernateAuthhorityTest();
		int userId=hat.Query(loginName, password);
		if(userId>=0){
			System.out.println("��½�ɹ�");
		}else if(userId==-1){
			System.out.println("�˻����������");
		}
		return userId;
	}
	/**
	 * ��½�ɹ�ҳ��
	 */
	public void loginSuccessPage(){
		while(true){
			System.out.println("1.�޸��˺�2.�޸�����3.�޸ı�ע4.����Լ��Ľ�ɫ��Ϣ5.�����ɫ��Ӧ����Դ��Ϣ6.����ϵͳ����Ա7.�˳�");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				System.out.println("������ԭ�����û���");
				String loginName=in.next();
				System.out.println("����������:");
				String password=in.next();
				System.out.println("���������û�����");
				String newLoginName=in.next();
				ht.update_loginName(loginName, newLoginName, password);
				break;
			case 2:
				System.out.println("������ԭ�����û���");
				String loginName1=in.next();
				System.out.println("����������:");
				String password1=in.next();
				System.out.println("�����������룺");
				String newPassword=in.next();
				ht.update_password(loginName1, password1, newPassword);
				break;
			case 3:
				System.out.println("������ԭ�����û���");
				String loginName2=in.next();
				System.out.println("����������:");
				String password2=in.next();
				System.out.println("�����뱸ע��");
				String status=in.next();
				ht.update_status(loginName2, password2, status);
				break;
			case 4:
				ht.QueryRole(userId);
				break;
			case 5:
				System.out.println("�������Լ��Ľ�ɫ����");
				String roleName=in.next();
				ht.QueryResource(roleName);
				break;
			case 6:
				if(userId==1){
					systemPage();
				}else{
					System.out.println("�Բ����㲻�ǹ���Ա������ӵ���������");
				}
				break;
			case 7:
				System.exit(0);
			}
		}	
	}
	/**
	 * ϵͳ����Ա���⹦��ҳ��
	 */
	public void systemPage(){
		while(true){
			System.out.println("1.�޸�2.���3.ɾ��");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				modify();
				break;
			case 2:
				skim();
				break;
			case 3:
				delete();
				break;
			}
		}
	}
	/**
	 * �޸Ĺ���ҳ��
	 */
	public void modify(){
		while(true){
			System.out.println("1.��Դ��Ϣ����2.��ɫ��Ϣ����3.�û���Ϣ����");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				ModifyResource();
				break;
			case 2:
				ModifyRole();
				break;
			case 3:
				System.out.println("��������Ҫ�޸��û���Id:");
				int MUId=in.nextInt();
				System.out.println("�������޸ĵ�״̬:");
				String status=in.next();
				ht.modifyUserStatus(MUId, status);
				break;
			}
		}
	}
	/**
	 * �޸���Դ��Ϣҳ��
	 */
	public void ModifyResource(){
		System.out.println("1.������2.��״̬3.�ı�ע");
		int choice=in.nextInt();
		switch(choice){
		case 1:
			System.out.println("������Ҫ�޸ĵ���ԴId:");
			int resId1=in.nextInt();
			System.out.println("�������޸ĵ�����:");
			String resName=in.next();
			ht.modifyResourceName(resId1, resName);
			break;
		case 2:
			break;
		case 3:
			System.out.println("������Ҫ�޸ĵ���ԴId:");
			int resId2=in.nextInt();
			System.out.println("�������޸ĵı�ע:");
			String resdecr=in.next();
			ht.modifyResourceDescription(resId2, resdecr);
			break;
		}
	}
	/**
	 * ��ɫ��Ϣ�����޸�ҳ��
	 */
	public void ModifyRole(){
		System.out.println("1.������2.��״̬3.�ı�ע");
		int choice=in.nextInt();
		switch(choice){
		case 1:
			System.out.println("������Ҫ�޸ĵĽ�ɫId:");
			int rId1=in.nextInt();
			System.out.println("�������޸ĵ�����:");
			String rName=in.next();
			ht.modifyRoleName(rId1, rName);
			break;
		case 2:
			System.out.println("������Ҫ�޸ĵĽ�ɫId:");
			int rId2=in.nextInt();
			System.out.println("�������޸ĵ�״̬:");
			String status=in.next();
			ht.modifyRoleStatus(rId2, status);
			break;
		case 3:
			System.out.println("������Ҫ�޸ĵĽ�ɫId:");
			int rId3=in.nextInt();
			System.out.println("�������޸ĵ�����:");
			String description=in.next();
			ht.modifyRoleDescription(rId3, description);
			break;
		}
	}
	/**
	 * �������ҳ��
	 */
	public void skim(){
		while(true){
			System.out.println("1.����û��б�2.��ɫ�б�3.��Դ�б�4.���ȫ��");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				ht.QueryUser();
				break;
			case 2:
				ht.QueryRole();
				break;
			case 3:
				ht.QueryResource();
				break;
			case 4:
				ht.allTable();
				break;
			}
		}
	}
	/**
	 * ɾ������ҳ��
	 */
	public void delete(){
		while(true){
			System.out.println("1.ɾ���û���Ϣ2.��ɫ��Ϣ3.��Դ��Ϣ");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				System.out.println("����������ɾ���û���Id");
				int UId=in.nextInt();
				ht.deleteUser(UId);
				break;
			case 2:
				System.out.println("����������ɾ����ɫ��Id");
				int RId=in.nextInt();
				ht.deleteRole(RId);
				break;
			case 3:
				System.out.println("����������ɾ����ɫ��Id");
				int ResId=in.nextInt();
				ht.deleteResource(ResId);
				break;
			}
		}
	}
}
