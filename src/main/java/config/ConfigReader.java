package config;

import java.util.List;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

	public static final Logger LOGGER;
	private static Configurations configs;
	private static XMLConfiguration config;

	static {
		LOGGER = LogManager.getLogger(ConfigReader.class.getName());
		configs = new Configurations();
		try {

			config = configs.xml(ConfigReader.class.getResource("/config.xml"));
			config.setThrowExceptionOnMissing(true);

		} catch (ConfigurationException e) {
			LOGGER.error("Failed to read the config.xml ", e);
		}
	}

	/**
	 * Read value of given configXpath.
	 * 
	 * @param configXpath xpath key for the needed value
	 * @return string value of the config
	 */
	public static String getConfig(String configXpath) {

		String configValue = null;
		configValue = config.getString(configXpath);

		return configValue;
	}

	/**
	 * Read values of config and return in form of a list
	 * 
	 * @param configXpath xpath key for the needed values
	 * @return List<String>
	 */
	public static List<String> readConfigList(String configXpath) {

		return config.getList(String.class, configXpath);

	}

	private ConfigReader() {
	}
}
