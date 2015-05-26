package net.sxnic.demo.test.base;

/**
 * 
 * @Description: 接口测试类
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015年5月26日
 */
public class InterfaceTest {

	public interface I {
		void save();
	}

	public class C implements I {

		@Override
		public void save() {
			System.out.println("===接口测试类===这是save方法===");
		}
	}

	public void runC() {
		C c = new C();
		c.save();
	}

	public static void main(String[] args) {
		InterfaceTest it = new InterfaceTest();
		it.runC();
	}
}
