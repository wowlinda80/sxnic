package net.sxnic.demo.test.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 循环测试类
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015年5月25日
 */
public class LoopTest {

	public void testFor() {
		for (int i = 0; i < 10; i++) {
			System.out.println("循环测试for===i===" + i);
		}
	}

	public void testFor1() {
		// <>里是泛型
		List<User> users = new ArrayList<User>();
		users.add(new User("张三"));
		users.add(new User("李四"));
		users.add(new User("王五"));

		for (User u : users) {
			System.out.println("循环测试for1===name===" + u.getName());
		}
	}

	public void testWhile() {
		int i = 0;
		while (i < 10) {
			System.out.println("循环测试while===i===" + i);
			i++;
		}
	}
	
	public void testLoopControl(){
		for (int i = 0; i < 10; i++) {
			System.out.println("循环测试控制===" + i);
			if(i==3){
				System.out.println("循环测试控制continue===i===" + i);
				continue;
			}else if(i==5){
				System.out.println("循环测试控制break===i===" + i);
				break;
			}
		}
	}

	/**
	 * 
	* @Description: 这是一个内部类 
	* @Author syf121@163.com
	* @Version 1.0
	* @CreateDate:2015年5月26日
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
