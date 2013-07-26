package com.example.misprimerospuzzles.utils;

import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

/**
 * Manejo de propiedades
 * 
 * @author Luis
 * 
 */
public class PropertyUtils {

	private Properties properties;

	public PropertyUtils(Context context) {
		loadProperties(context);
	}

	/**
	 * Obtiene una propiedad
	 * 
	 * @param key
	 *            clave de la propiedad
	 * @return valor de la propiedad
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Carga las propiedades
	 * 
	 */
	public void loadProperties(Context context) {
		String[] fileList = { "app.properties" };
		for (int i = fileList.length - 1; i >= 0; i--) {
			String file = fileList[i];
			try {
				InputStream fileStream = context.getAssets().open(file);
				properties = new Properties();
				properties.load(fileStream);
				fileStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
