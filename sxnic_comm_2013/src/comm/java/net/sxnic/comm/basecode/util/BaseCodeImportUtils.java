package net.sxnic.comm.basecode.util;
 
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.BaseCode;
import net.sxnic.comm.basecode.BaseCodeManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * BaseCode导入工具类
 * @author 孙宇飞
 *
 */
public class BaseCodeImportUtils {

	private static final Logger log = LoggerFactory
			.getLogger(BaseCodeImportUtils.class);

	/**
	 * 基本码表初始化。方法内部先对表中的数据进行清空操作，再插入。为初始化所用
	 * 
	 * @param basecodeManager
	 * @param file
	 * @throws IOException
	 */
	public static void importBaseCode(BaseCodeManager basecodeManager,String cyear, File file)
			throws IOException {

		List<String> lines = FileUtils.readLines(file, "utf-8");

		log.debug("clear table data.............");

		basecodeManager.clear();

		log.debug("init basecode start...........");

		int size = lines.size();
		importBaseCode(basecodeManager,cyear, (String[]) lines
				.toArray(new String[size]));

		log.debug("init basecode over............");
	}

	/**
	 * 导入基本码表。方法内部先对表中的数据进行清空操作，再插入。为初始化所用
	 * 
	 * @param basecodeManager
	 * @param data
	 * @throws IOException
	 */
	public static void importBaseCode(BaseCodeManager basecodeManager,String cyear,
			String data) throws IOException {

		String[] lines = StringUtils.split(data, '\n');

		log.debug("clear table data.............");

		basecodeManager.clear();

		log.debug("init basecode start...........");

		importBaseCode(basecodeManager,cyear, lines);

		log.debug("init basecode over............");
	}

	/**
	 * 导入基本码表。无视重复直接插入数据
	 * 
	 * @param basecodeManager
	 * @param lines
	 */
	public static void importBaseCode(BaseCodeManager basecodeManager,String cyear,
			String[] lines) {

		for (String line : lines) {
			if (StringUtils.isBlank(line)
					|| line.startsWith(CommConstant.TXT_IGNORE_CHAR)) {
				continue;
			}

			String[] m = line.split(",");

			log.debug(m[0] + "-----" + m[1]);
			BaseCode bc = basecodeManager.getBaseCode(m[0].trim(), m[2].trim(),"");
			if(bc ==null){
				bc = new BaseCode();
				bc.setSortCode(m[0].trim());
				bc.setSortName(m[1].trim());
				bc.setInfoCode(m[2].trim());
				bc.setInfoName(m[3].trim());
				if (m.length > 4) {
					bc.setInfoIndex(m[4].trim());
				}
				bc.setCyear(cyear);
				
				log.debug("===create basecode success==="+bc.getSortCode()+"==="+bc.getInfoCode());
				
			}else{
				bc.setSortCode(m[0].trim());
				bc.setSortName(m[1].trim());
				bc.setInfoCode(m[2].trim());
				bc.setInfoName(m[3].trim());
				if (m.length > 4) {
					bc.setInfoIndex(m[4].trim());
				}
				bc.setCyear(cyear);
				log.debug("===update basecode success===" + bc.getSortCode() + "===" + bc.getInfoCode());
			}			

			basecodeManager.save(bc);
		}
	}

}
