package net.sxnic.comm.utils;

import java.io.File;
import java.io.IOException;

public class FileCase {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		try {
			CommUtils.fileCase(new File("F:\\Devolpment\\WEB 设计\\163\\"), "1");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
