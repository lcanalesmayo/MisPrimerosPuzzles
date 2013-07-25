package com.example.misprimerospuzzles;

import java.io.InputStream;
import java.util.Properties;

/**
 * Manejo de propiedades
 * 
 * @author Luis
 * 
 */
public class PropertyUtils {

	private static Properties properties;

	static {
		properties = loadProperties();
	}

	/**
	 * Obtiene una propiedad
	 * 
	 * @param key
	 *            clave de la propiedad
	 * @return valor de la propiedad
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Carga las propiedades
	 * 
	 * @return propiedades
	 */
	private static Properties loadProperties() {
		String[] fileList = { "app.properties" };
		Properties prop = new Properties();
		for (int i = fileList.length - 1; i >= 0; i--) {
			String file = fileList[i];
			try {
				InputStream fileStream = MainActivity.getInstance().getAssets()
						.open(file);
				prop.load(fileStream);
				fileStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
