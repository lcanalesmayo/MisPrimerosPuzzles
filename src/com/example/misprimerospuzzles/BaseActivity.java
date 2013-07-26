package com.example.misprimerospuzzles;

import android.app.Activity;

import com.example.misprimerospuzzles.utils.PropertyUtils;

public class BaseActivity extends Activity {
	protected PropertyUtils propertyUtils;

	public PropertyUtils getPropertyUtils() {
		return propertyUtils;
	}

	public void setPropertyUtils(PropertyUtils propertyUtils) {
		this.propertyUtils = propertyUtils;
	}
}
