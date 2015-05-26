package net.sxnic.demo.test.base;

/**
 * 
 * @Description: ѡ�������
 * @Author syf121@163.com
 * @Version 1.0
 * @CreateDate:2015��5��25��
 */
public class ChoiceTest {

	public void testIfElse() {
		int i = 0;
		if (i == 0) {
			System.out.println("if else����===i===" + 0);
		} else {
			System.out.println("if else����===i===" + i);
		}
	}

	public void testSwitch() {
		int i = 1;
		switch (i) {
		case 1:
			System.out.println("Switch����===i===" + 1);
			// break������
			break;
		case 2:
			System.out.println("Switch����===i===" + 2);
			break;
		}
	}

	/**
	 * ��Ԫ���ʽ����
	 */
	public void testTernaryExpression() {
		String s = "1";
		System.out.println("��Ԫ���ʽ����===s===" + s);

		String s1 = ("2".equals(s)) ? "11" : "22";
		System.out.println("��Ԫ���ʽ����===s1===" + s1);
	}

	public static void main(String[] args) {
		ChoiceTest c = new ChoiceTest();
		c.testIfElse();
		c.testSwitch();
		c.testTernaryExpression();
	}

}
