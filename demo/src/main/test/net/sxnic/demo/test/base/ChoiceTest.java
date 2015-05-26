package net.sxnic.demo.test.base;

/**
 * 
 * @Description: 选择测试类
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015年5月25日
 */
public class ChoiceTest {

	public void testIfElse() {
		int i = 0;
		if (i == 0) {
			System.out.println("if else测试===i===" + 0);
		} else {
			System.out.println("if else测试===i===" + i);
		}
	}

	public void testSwitch() {
		int i = 1;
		switch (i) {
		case 1:
			System.out.println("Switch测试===i===" + 1);
			// break必须有
			break;
		case 2:
			System.out.println("Switch测试===i===" + 2);
			break;
		}
	}

	/**
	 * 三元表达式测试
	 */
	public void testTernaryExpression() {
		String s = "1";
		System.out.println("三元表达式测试===s===" + s);

		String s1 = ("2".equals(s)) ? "11" : "22";
		System.out.println("三元表达式测试===s1===" + s1);
	}

	public static void main(String[] args) {
		ChoiceTest c = new ChoiceTest();
		c.testIfElse();
		c.testSwitch();
		c.testTernaryExpression();
	}

}
