package net.sxnic.demo.test.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @Description:泛型测试类 Java SE5版本开始支持泛型
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015年5月26日
 */
public class GenericsTest {

	public void testArray() {
		String[] ss = new String[3];
		ss[0] = "1";
		ss[1] = "2";
		ss[2] = "3";

		for (String s : ss) {
			System.out.println(s);
		}

	}

	public void testSet() {
		// JDK1.5之前的通用迭代器
		Set set1 = new HashSet<String>();
		set1.add("1");
		set1.add("2");

		Iterator iter = set1.iterator();
		while (iter.hasNext()) {
			String temp = (String) iter.next();
			System.out.println(temp);
		}

		// JDK1.5之后的泛型
		Set<String> sets = new HashSet<String>();
		sets.add("1");
		sets.add("2");

		for (String s : sets) {
			System.out.println(s);
		}
	}

	public interface Dao<T> {
		void save(T t);
	}

	public class HibernateDao<T> implements Dao {

		public T t;

		@Override
		public void save(Object o) {
			System.out.println(t + "----" + o);
		}

	}

	public void testRun() {

		HibernateDao hd = new HibernateDao<User>();
		User u = new User("张三");
		hd.t = u;
		hd.save(u);

	}

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

		@Override
		public String toString() {
			return "User [name=" + name + "]";
		}
	}

	public static void main(String[] args) {
		GenericsTest gt = new GenericsTest();
		gt.testRun();
	}

}
