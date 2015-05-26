package net.sxnic.demo.test.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: ѭ��������
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015��5��25��
 */
public class LoopTest {

	public void testFor() {
		for (int i = 0; i < 10; i++) {
			System.out.println("ѭ������for===i===" + i);
		}
	}

	public void testFor1() {
		// <>���Ƿ���
		List<User> users = new ArrayList<User>();
		users.add(new User("����"));
		users.add(new User("����"));
		users.add(new User("����"));

		for (User u : users) {
			System.out.println("ѭ������for1===name===" + u.getName());
		}
	}

	public void testWhile() {
		int i = 0;
		while (i < 10) {
			System.out.println("ѭ������while===i===" + i);
			i++;
		}
	}
	
	public void testLoopControl(){
		for (int i = 0; i < 10; i++) {
			System.out.println("ѭ�����Կ���===" + i);
			if(i==3){
				System.out.println("ѭ�����Կ���continue===i===" + i);
				continue;
			}else if(i==5){
				System.out.println("ѭ�����Կ���break===i===" + i);
				break;
			}
		}
	}

	/**
	 * 
	* @Description: ����һ���ڲ��� 
	* @Author syf121@163.com
	* @Version 1.0
	* @CreateDate:2015��5��26��
	 */
	public class User {
		private String name;

		public User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		LoopTest lt = new LoopTest();
		lt.testFor();
		lt.testFor1();
		lt.testWhile();
		lt.testLoopControl();
	}

}
