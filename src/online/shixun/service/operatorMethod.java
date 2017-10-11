package online.shixun.service;

import java.util.Scanner;

import online.shixun.test.HibernateAuthhorityTest;

public class operatorMethod {
	int userId=-1;
	HibernateAuthhorityTest ht=new HibernateAuthhorityTest();
	Scanner in=new Scanner(System.in);
	/**
	 * 主菜单页面
	 */
	public void menu(){
		
		while(true){
			System.out.println("-----------------登录界面-----------------");
			System.out.println("1.登录  2.注册  3.退出");
			System.out.println("请输入1/2/3");
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
				System.out.println("输入错误，请重新输入");
			}
		}
	}
	/**
	 * 注册用户
	 */
	public void register(){
		System.out.println("请输入您即将注册的用户名:");
		String loginName=in.next();
		System.out.println("密码:");
		String password=in.next();
		System.out.println("备注:");
		String status=in.next();
		HibernateAuthhorityTest hat=new HibernateAuthhorityTest();
		hat.saveUser(loginName, password, status);
	}
	/**
	 * 登录验证
	 */
	public int login(){
		System.out.println("请输入用户名:");
		String loginName=in.next();
		System.out.println("密码:");
		String password=in.next();
		HibernateAuthhorityTest hat=new HibernateAuthhorityTest();
		int userId=hat.Query(loginName, password);
		if(userId>=0){
			System.out.println("登陆成功");
		}else if(userId==-1){
			System.out.println("账户或密码错误！");
		}
		return userId;
	}
	/**
	 * 登陆成功页面
	 */
	public void loginSuccessPage(){
		while(true){
			System.out.println("1.修改账号2.修改密码3.修改备注4.浏览自己的角色信息5.浏览角色对应的资源信息6.我是系统管理员7.退出");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				System.out.println("请输入原来的用户名");
				String loginName=in.next();
				System.out.println("请输入密码:");
				String password=in.next();
				System.out.println("请输入新用户名：");
				String newLoginName=in.next();
				ht.update_loginName(loginName, newLoginName, password);
				break;
			case 2:
				System.out.println("请输入原来的用户名");
				String loginName1=in.next();
				System.out.println("请输入密码:");
				String password1=in.next();
				System.out.println("请输入新密码：");
				String newPassword=in.next();
				ht.update_password(loginName1, password1, newPassword);
				break;
			case 3:
				System.out.println("请输入原来的用户名");
				String loginName2=in.next();
				System.out.println("请输入密码:");
				String password2=in.next();
				System.out.println("请输入备注：");
				String status=in.next();
				ht.update_status(loginName2, password2, status);
				break;
			case 4:
				ht.QueryRole(userId);
				break;
			case 5:
				System.out.println("请输入自己的角色名称");
				String roleName=in.next();
				ht.QueryResource(roleName);
				break;
			case 6:
				if(userId==1){
					systemPage();
				}else{
					System.out.println("对不起，你不是管理员，不能拥有这个功能");
				}
				break;
			case 7:
				System.exit(0);
			}
		}	
	}
	/**
	 * 系统管理员特殊功能页面
	 */
	public void systemPage(){
		while(true){
			System.out.println("1.修改2.浏览3.删除");
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
	 * 修改功能页面
	 */
	public void modify(){
		while(true){
			System.out.println("1.资源信息功能2.角色信息功能3.用户信息功能");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				ModifyResource();
				break;
			case 2:
				ModifyRole();
				break;
			case 3:
				System.out.println("请输入您要修改用户的Id:");
				int MUId=in.nextInt();
				System.out.println("请输入修改的状态:");
				String status=in.next();
				ht.modifyUserStatus(MUId, status);
				break;
			}
		}
	}
	/**
	 * 修改资源信息页面
	 */
	public void ModifyResource(){
		System.out.println("1.改名称2.改状态3.改备注");
		int choice=in.nextInt();
		switch(choice){
		case 1:
			System.out.println("请输入要修改的资源Id:");
			int resId1=in.nextInt();
			System.out.println("请输入修改的名称:");
			String resName=in.next();
			ht.modifyResourceName(resId1, resName);
			break;
		case 2:
			break;
		case 3:
			System.out.println("请输入要修改的资源Id:");
			int resId2=in.nextInt();
			System.out.println("请输入修改的备注:");
			String resdecr=in.next();
			ht.modifyResourceDescription(resId2, resdecr);
			break;
		}
	}
	/**
	 * 角色信息功能修改页面
	 */
	public void ModifyRole(){
		System.out.println("1.改名称2.改状态3.改备注");
		int choice=in.nextInt();
		switch(choice){
		case 1:
			System.out.println("请输入要修改的角色Id:");
			int rId1=in.nextInt();
			System.out.println("请输入修改的名称:");
			String rName=in.next();
			ht.modifyRoleName(rId1, rName);
			break;
		case 2:
			System.out.println("请输入要修改的角色Id:");
			int rId2=in.nextInt();
			System.out.println("请输入修改的状态:");
			String status=in.next();
			ht.modifyRoleStatus(rId2, status);
			break;
		case 3:
			System.out.println("请输入要修改的角色Id:");
			int rId3=in.nextInt();
			System.out.println("请输入修改的名称:");
			String description=in.next();
			ht.modifyRoleDescription(rId3, description);
			break;
		}
	}
	/**
	 * 浏览功能页面
	 */
	public void skim(){
		while(true){
			System.out.println("1.浏览用户列表2.角色列表3.资源列表4.浏览全部");
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
	 * 删除功能页面
	 */
	public void delete(){
		while(true){
			System.out.println("1.删除用户信息2.角色信息3.资源信息");
			int choice=in.nextInt();
			switch(choice){
			case 1:
				System.out.println("请输入你想删除用户的Id");
				int UId=in.nextInt();
				ht.deleteUser(UId);
				break;
			case 2:
				System.out.println("请输入你想删除角色的Id");
				int RId=in.nextInt();
				ht.deleteRole(RId);
				break;
			case 3:
				System.out.println("请输入你想删除角色的Id");
				int ResId=in.nextInt();
				ht.deleteResource(ResId);
				break;
			}
		}
	}
}
