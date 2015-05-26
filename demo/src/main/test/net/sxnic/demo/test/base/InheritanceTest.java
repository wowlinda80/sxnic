package net.sxnic.demo.test.base;

/**
 * 
 * @Description: �̳в���
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015��5��26��
 */
public class InheritanceTest {

	/**
	 * 
	 * @Description:����
	 * @Author syf121@163.com
	 * @Version 1.0
	 * @CreateDate:2015��5��26��
	 */
	public class A {
		protected String id;


		public A() {
			super();
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}

	/**
	 * 
	 * @Description: ����
	 * @Author syf121@163.com
	 * @Version 1.0
	 * @CreateDate:2015��5��26��
	 */
	public class B extends A {
		private String name;

		public B(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public void newSubClass(){
		B b =new B("����");
		b.setId("1");
		
		System.out.println("===����B===name=="+b.getName());
		System.out.println("===����B===id=="+b.getId());
	}

	public static void main(String[] args) {
		InheritanceTest it = new InheritanceTest();
		it.newSubClass();
	}

}
