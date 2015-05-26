package net.sxnic.demo.test.base;

/**
 * 
 * @Description: �ӿڲ�����
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015��5��26��
 */
public class InterfaceTest {

	public interface I {
		void save();
	}

	public class C implements I {

		@Override
		public void save() {
			System.out.println("===�ӿڲ�����===����save����===");
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
