package net.sxnic.comm.property.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.property.Property;
import net.sxnic.comm.property.PropertyManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyImportUtils {
	
	private static final Logger log = LoggerFactory.getLogger(PropertyImportUtils.class);
	
	/**
	 * 属性表初始化
	 * 
	 * @param propertyManager
	 * @param file
	 * @throws IOException
	 * @throws EntityAlreadyExistsException
	 */
	public static void importProperty(PropertyManager propertyManager, File file)
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
	 * @param data String属性
	 * @throws IOException
	 * @throws EntityAlreadyExistsException
	 */
	public static void importProperty(PropertyManager propertyManager, String data)
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
	 * @param properties String数组
	 * @throws EntityAlreadyExistsException
	 */
	public static void importProperty(PropertyManager propertyManager,
			String[] properties) throws EntityAlreadyExistsException {
		for (String line : properties) {			
			if (StringUtils.isBlank(line) || line.startsWith("&")
					|| line.contains (CommConstant.TXT_IGNORE_CHARS)) {
				continue;
			}

			String[] m = line.split("%&%");
			Property p = new Property();
			p.setPropName(m[0].trim());
			p.setPropValue(m[1].trim());
			if (m.length > 2)
				p.setDescription(m[2].trim());

			propertyManager.create(p);
		}
	}

	/**
	 * @param propertyManager
	 * @param properties 属性数组
	 * @param clear 如果clear为true则表示本方法外部已经对表进行了清空操作，则在本方法内部不检测重复，直接插入。
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
					log.debug("property update success, property name is : "+pp.getPropName());
					continue;
				}
			}

			Property p = new Property();
			p.setPropName(m[0].trim());
			p.setPropValue(m[1].trim());
			if (m.length > 2)
				p.setDescription(m[2].trim());

			propertyManager.create(p);
			log.debug("property create success, property name is : "+p.getPropName());
		}
	}

}
