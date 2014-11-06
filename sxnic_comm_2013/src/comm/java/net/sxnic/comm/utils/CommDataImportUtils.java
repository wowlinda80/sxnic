package net.sxnic.comm.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.BaseCode;
import net.sxnic.comm.basecode.BaseCodeManager;
import net.sxnic.comm.property.Property;
import net.sxnic.comm.property.PropertyManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommDataImportUtils {

	private static final Log log = LogFactory.getLog(CommDataImportUtils.class);

	/**
	 * 基本码表初始化。方法内部先对表中的数据进行清空操作，再插入。为初始化所用
	 * 
	 * @param basecodeManager
	 * @param file
	 * @throws IOException
	 */

	public static void initBaseCode(BaseCodeManager basecodeManager, File file)
			throws IOException {

		List<String> lines = FileUtils.readLines(file, "utf-8");

		log.debug("clear table data.............");

		basecodeManager.clearTable();

		log.debug("init basecode start...........");

		int size = lines.size();
		importBaseCode(basecodeManager, (String[]) lines
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
	public static void initBaseCode(BaseCodeManager basecodeManager, String data)
			throws IOException {

		String[] lines = StringUtils.split(data, '\n');

		log.debug("clear table data.............");

		basecodeManager.clearTable();

		log.debug("init basecode start...........");

		importBaseCode(basecodeManager, lines);

		log.debug("init basecode over............");
	}

	/**
	 * 导入基本码表。无视重复直接插入数据
	 * 
	 * @param basecodeManager
	 * @param lines
	 */
	public static void importBaseCode(BaseCodeManager basecodeManager,
			String[] lines) {

		for (String line : lines) {
			if (StringUtils.isBlank(line)
					|| line.startsWith(CommConstant.TXT_IGNORE_CHAR)) {
				continue;
			}

			String[] m = line.split(",");

			log.debug(m[0] + "-----" + m[1]+ "-----" + m[2]+ "-----" + m[3]+ "-----" + m[4] );
			BaseCode bc = new BaseCode();
			bc.setSortCode(m[0].trim());
			bc.setSortName(m[1].trim());
			bc.setInfoCode(m[2].trim());
			bc.setInfoName(m[3].trim());
			bc.setInfoIndex(m[4].trim());
			bc.setCyear(String.valueOf(Calendar.getInstance()
					.get(Calendar.YEAR)));

			basecodeManager.save(bc);
		}
	}

	/**
	 * 导入基本码表
	 * 
	 * @param basecodeManager
	 *            基本码表的基本业务类
	 * @param lines
	 *            数据集
	 * @param clear
	 *            如果clear为true则表示本方法外部已经对表进行了清空操作，则在本方法内部不检测重复，直接插入。
	 *            如果clear为false则在本法内部需要检验每条数据是否跟现有表中的数据重复，如果有重复则不插入，否则插入
	 *            如果clear为null 则视为false
	 */
	public static void importBaseCode(BaseCodeManager basecodeManager,
			String[] lines, boolean clear) {

		for (String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}

			String[] m = line.split(",");

			if (!clear) {
				BaseCode b = basecodeManager.getBaseCode(m[0].trim(), m[1]
						.trim(), m[2].trim(), m[3].trim());
				if (b != null) {
					continue;
				}
			}

			log.debug(m[0] + "-----" + m[1]);
			BaseCode bc = new BaseCode();
			bc.setSortCode(m[0].trim());
			bc.setSortName(m[1].trim());
			bc.setInfoCode(m[2].trim());
			bc.setInfoName(m[3].trim());
			bc.setInfoIndex(m[4].trim());
			bc.setCyear(String.valueOf(Calendar.getInstance()
					.get(Calendar.YEAR)));

			basecodeManager.save(bc);
		}

	}

	/**
	 * 属性表初始化
	 * 
	 * @param propertyManager
	 * @param file
	 * @throws IOException
	 * @throws EntityAlreadyExistsException
	 */
	public static void initProperty(PropertyManager propertyManager, File file)
			throws IOException, EntityAlreadyExistsException {
		List<String> lines = FileUtils.readLines(file, "utf-8");

		log.debug("clear property table data.............");

		propertyManager.clear();

		log.debug("init property start...........");

		int size = lines.size();
		importProperty(propertyManager, (String[]) lines
				.toArray(new String[size]));

		log.debug("init property over............");
	}

	/**
	 * 属性表初始化
	 * 
	 * @param propertyManager
	 * @param data
	 *            String属性
	 * @throws IOException
	 * @throws EntityAlreadyExistsException
	 */
	public static void initProperty(PropertyManager propertyManager, String data)
			throws IOException, EntityAlreadyExistsException {
		String[] lines = StringUtils.split(data, '\n');

		log.debug("clear property table data.............");

		propertyManager.clear();

		log.debug("init property start...........");
		importProperty(propertyManager, lines);
		log.debug("init property over............");
	}

	/**
	 * 属性表导入
	 * 
	 * @param propertyManager
	 * @param properties
	 *            String数组
	 * @throws EntityAlreadyExistsException
	 */
	public static void importProperty(PropertyManager propertyManager,
			String[] properties) throws EntityAlreadyExistsException {
		for (String line : properties) {
			if (StringUtils.isBlank(line) || line.startsWith("&")
					|| line.startsWith(CommConstant.TXT_IGNORE_CHAR)) {
				continue;
			}

			String[] m = line.split("%&%");
			Property p = new Property();
			p.setPropName(m[0].trim());
			p.setPropValue(m[1].trim());
			if (m.length > 2)
				p.setDescription(m[2].trim());

			propertyManager.save(p);
		}
	}

	/**
	 * @param propertyManager 属性类的业务操作类
	 * @param properties 属性数组
	 * @param clear
	 *            如果clear为true则表示本方法外部已经对表进行了清空操作，则在本方法内部不检测重复，直接插入。
	 *            如果clear为false则在本法内部需要检验每条数据是否跟现有表中的数据重复，如果有重复则不插入，否则插入
	 *            如果clear为null 则视为false
	 * @throws EntityAlreadyExistsException
	 */
	public static void importProperty(PropertyManager propertyManager,
			String[] properties, boolean clear)
			throws EntityAlreadyExistsException {

		for (String line : properties) {

			if (StringUtils.isBlank(line) || line.startsWith("&")) {
				continue;
			}

			String[] m = line.split("%&%");

			// 如果外部没有清空数据，则根据name查询property数据，如果存在，则更新数据，如果没有则插入
			if (!clear) {
				Property pp = propertyManager.getByName(m[0].trim());
				if (pp != null) {

					pp.setPropValue(m[1].trim());
					if (m.length > 2)
						pp.setDescription(m[2].trim());

					propertyManager.update(pp);
					log.debug("property update success, property name is : "
							+ pp.getPropName());
					continue;
				}
			}

			Property p = new Property();
			p.setPropName(m[0].trim());
			p.setPropValue(m[1].trim());
			if (m.length > 2)
				p.setDescription(m[2].trim());

			propertyManager.save(p);
			log.debug("property create success, property name is : "
					+ p.getPropName());
		}
	}

	public String getClassPath(Object obj){
		return null;
	}

}
