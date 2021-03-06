package com.jdroid.android.context;

import java.util.Locale;
import java.util.Set;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import com.jdroid.android.AbstractApplication;
import com.jdroid.android.debug.ExceptionType;
import com.jdroid.android.utils.SharedPreferencesHelper;
import com.jdroid.java.http.Server;
import com.jdroid.java.utils.PropertiesUtils;
import com.jdroid.java.utils.ValidationUtils;

public class AppContext {
	
	private static final String PROPERTIES_RESOURCE_NAME = "settings.properties";
	private static final String LOCAL_PROPERTIES_RESOURCE_NAME = "settings.local.properties";
	
	public static final String USER_DATA_MOCKED = "userDataMocked";
	public static final String ADS_ENABLED = "adsEnabled";
	private static final String FIRST_SESSION_TIMESTAMP = "firstSessionTimestamp";
	public static final String HTTP_MOCK_ENABLED = "httpMockEnabled";
	public static final String HTTP_MOCK_SLEEP = "httpMockSleep";
	public static final String HTTP_MOCK_CRASH_TYPE = "httpMockCrashType";
	
	// Environment
	private String localIp;
	private Environment environment;
	private Boolean isFreeApp;
	private String installationSource;
	private Server server;
	private String serverApiVersion;
	
	// Social
	private String contactUsEmail;
	private String googleProjectId;
	private String facebookAppId;
	
	// Debug
	private Boolean debugSettings;
	
	// Ads
	private Boolean adsEnabled;
	private String adUnitId;
	private Set<String> testDevicesIds;
	
	// Google Analytics
	private Boolean googleAnalyticsEnabled;
	private Boolean googleAnalyticsDebugEnabled;
	private String googleAnalyticsTrackingId;
	
	// CrashlyticsEnabled
	private Boolean crashlyticsEnabled;
	private Boolean crashlyticsDebugEnabled;
	
	public AppContext() {
		PropertiesUtils.loadProperties(LOCAL_PROPERTIES_RESOURCE_NAME);
		PropertiesUtils.loadProperties(PROPERTIES_RESOURCE_NAME);
		
		localIp = PropertiesUtils.getStringProperty("local.ip");
		environment = Environment.valueOf(PropertiesUtils.getStringProperty("environment.name",
			Environment.DEV.toString()));
		
		debugSettings = PropertiesUtils.getBooleanProperty("debug.settings", false);
		isFreeApp = PropertiesUtils.getBooleanProperty("free.app");
		server = findServerByName(PropertiesUtils.getStringProperty("server.name"));
		serverApiVersion = PropertiesUtils.getStringProperty("server.api.version");
		
		contactUsEmail = PropertiesUtils.getStringProperty("mail.contact");
		if (!ValidationUtils.isValidEmail(contactUsEmail)) {
			contactUsEmail = null;
		}
		
		googleProjectId = PropertiesUtils.getStringProperty("google.projectId");
		facebookAppId = PropertiesUtils.getStringProperty("facebook.app.id");
		
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
		
		crashlyticsEnabled = PropertiesUtils.getBooleanProperty("crashlytics.enabled", false);
		crashlyticsDebugEnabled = PropertiesUtils.getBooleanProperty("crashlytics.debug.enabled", false);
		
		installationSource = PropertiesUtils.getStringProperty("installation.source", "GooglePlay");
	}
	
	protected Server findServerByName(String name) {
		return null;
	}
	
	public Server getServer() {
		return getServer(server);
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
	
	public Boolean isHttpMockEnabled() {
		return !isProductionEnvironment()
				&& PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean(
					HTTP_MOCK_ENABLED, false);
	}
	
	public Integer getHttpMockSleepDuration() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean(HTTP_MOCK_SLEEP,
			false) ? 10 : null;
	}
	
	public ExceptionType getHttpMockExceptionType() {
		return ExceptionType.find(PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getString(
			HTTP_MOCK_CRASH_TYPE, null));
	}
	
	public Boolean isUserDataMocked() {
		return PreferenceManager.getDefaultSharedPreferences(AbstractApplication.get()).getBoolean(USER_DATA_MOCKED,
			false);
	}
	
	public String getLocalIp() {
		return localIp;
	}
	
	public String getInstallationSource() {
		return installationSource;
	}
	
	public Boolean isCrashlyticsEnabled() {
		return crashlyticsEnabled;
	}
	
	public Boolean isCrashlyticsDebugEnabled() {
		return crashlyticsDebugEnabled;
	}
	
	public void saveFirstSessionTimestamp() {
		Long firstSessionTimestamp = getFirstSessionTimestamp();
		if (firstSessionTimestamp == null) {
			SharedPreferencesHelper.get().savePreference(FIRST_SESSION_TIMESTAMP, System.currentTimeMillis());
		}
	}
	
	public Long getFirstSessionTimestamp() {
		return SharedPreferencesHelper.get().loadPreferenceAsLong(FIRST_SESSION_TIMESTAMP);
	}
	
	public Long getDaysSinceFirstSession() {
		Long firstSessionTimestamp = getFirstSessionTimestamp();
		if (firstSessionTimestamp != null) {
			return (System.currentTimeMillis() - getFirstSessionTimestamp()) / DateUtils.DAY_IN_MILLIS;
		} else {
			return 0L;
		}
	}
	
	public String getServerApiVersion() {
		return serverApiVersion;
	}
	
	public String getContactUsEmail() {
		return contactUsEmail;
	}
	
	public String getTwitterAccount() {
		return null;
	}
	
	public String getFacebookPageId() {
		return null;
	}
	
	public String getGooglePlusCommunityId() {
		return null;
	}
}
