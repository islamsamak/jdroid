package com.jdroid.android.context;

import java.util.Locale;
import java.util.Set;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.jdroid.android.AbstractApplication;
import com.jdroid.java.http.Server;
import com.jdroid.java.utils.PropertiesUtils;

/**
 * 
 * @author Maxi Rosson
 */
public class AppContext {
	
	private static final String PROPERTIES_RESOURCE_NAME = "settings.properties";
	private static final String LOCAL_PROPERTIES_RESOURCE_NAME = "settings.local.properties";
	
	private static final String ADS_ENABLED = "adsEnabled";
	
	// Environment
	private String localIp;
	private Environment environment;
	private Boolean isFreeApp;
	private String installationSource;
	
	// Social
	private String googleProjectId;
	private String facebookAppId;
	
	// Debug
	private Boolean debugSettings;
	private Boolean debugScreenshots;
	private Boolean crashReportsEnabled;
	
	// Ads
	private Boolean adsEnabled;
	private String adUnitId;
	private Set<String> testDevicesIds;
	
	// Google Analytics
	private Boolean googleAnalyticsEnabled;
	private Boolean googleAnalyticsDebugEnabled;
	private String googleAnalyticsTrackingId;
	
	// Flurry
	private Boolean flurryEnabled;
	private String flurryApiKey;
	private Boolean flurryDebugEnabled;
	
	// Crittercism
	private Boolean crittercismEnabled;
	private String crittercismAppId;
	private Boolean crittercismPremium;
	
	public AppContext() {
		PropertiesUtils.loadProperties(LOCAL_PROPERTIES_RESOURCE_NAME);
		PropertiesUtils.loadProperties(PROPERTIES_RESOURCE_NAME);
		
		localIp = PropertiesUtils.getStringProperty("local.ip");
		environment = Environment.valueOf(PropertiesUtils.getStringProperty("environment.name",
			Environment.DEV.toString()));
		googleProjectId = PropertiesUtils.getStringProperty("google.projectId");
		facebookAppId = PropertiesUtils.getStringProperty("facebook.app.id");
		debugSettings = PropertiesUtils.getBooleanProperty("debug.settings", false);
		debugScreenshots = PropertiesUtils.getBooleanProperty("debug.screenshots", false);
		crashReportsEnabled = PropertiesUtils.getBooleanProperty("crash.reporting.enabled", false);
		isFreeApp = PropertiesUtils.getBooleanProperty("free.app");
		
		adsEnabled = PropertiesUtils.getBooleanProperty("ads.enabled", false);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get());
		if (!sharedPreferences.contains(ADS_ENABLED)) {
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(ADS_ENABLED, adsEnabled);
			editor.commit();
		}
		
		adUnitId = PropertiesUtils.getStringProperty("ads.adUnitId");
		testDevicesIds = PropertiesUtils.getStringSetProperty("ads.tests.devices.ids");
		googleAnalyticsEnabled = PropertiesUtils.getBooleanProperty("google.analytics.enabled", false);
		googleAnalyticsDebugEnabled = PropertiesUtils.getBooleanProperty("google.analytics.debug.enabled", false);
		googleAnalyticsTrackingId = PropertiesUtils.getStringProperty("google.analytics.trackingId");
		flurryEnabled = PropertiesUtils.getBooleanProperty("flurry.enabled", false);
		flurryApiKey = PropertiesUtils.getStringProperty("flurry.apikey");
		flurryDebugEnabled = PropertiesUtils.getBooleanProperty("flurry.debug.enabled", false);
		crittercismEnabled = PropertiesUtils.getBooleanProperty("crittercism.enabled", false);
		crittercismAppId = PropertiesUtils.getStringProperty("crittercism.appId");
		crittercismPremium = PropertiesUtils.getBooleanProperty("crittercism.premium", false);
		installationSource = PropertiesUtils.getStringProperty("installation.source", "GooglePlay");
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Server> T getServer(Server defaultServer) {
		if (isProductionEnvironment() || !displayDebugSettings()) {
			return (T)defaultServer;
		} else {
			Class<?> clazz = defaultServer.getClass().getDeclaringClass() != null ? defaultServer.getClass().getDeclaringClass()
					: defaultServer.getClass();
			return (T)defaultServer.instance(PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getString(
				clazz.getSimpleName(), defaultServer.getName()).toUpperCase(Locale.US));
		}
	}
	
	/**
	 * @return The Google project ID acquired from the API console
	 */
	public String getGoogleProjectId() {
		return googleProjectId;
	}
	
	/**
	 * @return The registered Facebook app ID that is used to identify this application for Facebook.
	 */
	public String getFacebookAppId() {
		return facebookAppId;
	}
	
	/**
	 * @return Whether the application should display the debug settings
	 */
	public Boolean displayDebugSettings() {
		return debugSettings;
	}
	
	public Boolean isDebugScreenshots() {
		return debugScreenshots;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	
	/**
	 * @return Whether the application is running on a production environment
	 */
	public Boolean isProductionEnvironment() {
		return environment.equals(Environment.PROD);
	}
	
	/**
	 * @return Whether the application is free or not
	 */
	public Boolean isFreeApp() {
		return isFreeApp;
	}
	
	/**
	 * @return Whether the application has ads enabled or not
	 */
	public Boolean areAdsEnabled() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean(ADS_ENABLED,
			adsEnabled);
	}
	
	public void enableAds(Boolean enable) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get());
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(ADS_ENABLED, enable);
		editor.commit();
	}
	
	/**
	 * @return The MD5-hashed ID of the devices that should display mocked ads
	 */
	public Set<String> getTestDevicesIds() {
		return testDevicesIds;
	}
	
	/**
	 * @return The AdMob Publisher ID
	 */
	public String getAdUnitId() {
		return adUnitId;
	}
	
	/**
	 * @return Whether the application has Google Analytics enabled or not
	 */
	public Boolean isGoogleAnalyticsEnabled() {
		return googleAnalyticsEnabled;
	}
	
	/**
	 * @return The Google Analytics Tracking ID
	 */
	public String getGoogleAnalyticsTrackingId() {
		return googleAnalyticsTrackingId;
	}
	
	public Boolean isGoogleAnalyticsDebugEnabled() {
		return googleAnalyticsDebugEnabled;
	}
	
	public Boolean isFlurryEnabled() {
		return flurryEnabled;
	}
	
	public String getFlurryApiKey() {
		return flurryApiKey;
	}
	
	public Boolean isFlurryDebugEnabled() {
		return flurryDebugEnabled;
	}
	
	public Boolean isHttpMockEnabled() {
		return !isProductionEnvironment()
				&& PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean(
					"httpMockEnabled", false);
	}
	
	public Integer getHttpMockSleepDuration() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean("httpMockSleep",
			false) ? 10 : null;
	}
	
	public String getCrashType() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getString("crashType", null);
	}
	
	public String getHttpMockCrashType() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getString("httpMockCrashType",
			null);
	}
	
	public Boolean isCrittercismEnabled() {
		return crittercismEnabled;
	}
	
	public String getCrittercismAppId() {
		return crittercismAppId;
	}
	
	public Boolean isCrittercismPremium() {
		return crittercismPremium;
	}
	
	public String getLocalIp() {
		return localIp;
	}
	
	public String getInstallationSource() {
		return installationSource;
	}
	
	public Boolean isCrashReportsEnabled() {
		return crashReportsEnabled;
	}
}