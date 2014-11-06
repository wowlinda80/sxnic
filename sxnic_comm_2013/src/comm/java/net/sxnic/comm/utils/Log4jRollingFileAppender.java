package net.sxnic.comm.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;

public class Log4jRollingFileAppender extends RollingFileAppender {

	@Override
	public synchronized void setFile(String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {

		fileName = PropertyUtil.getLogFilePaht() + File.separator + fileName;

		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);

		if (append) {
			File f = new File(fileName);
			((CountingQuietWriter) qw).setCount(f.length());
		}
	}

}
