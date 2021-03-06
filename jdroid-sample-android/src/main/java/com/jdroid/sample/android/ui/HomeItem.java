package com.jdroid.sample.android.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.jdroid.android.ActionItem;
import com.jdroid.android.ActivityLauncher;
import com.jdroid.sample.android.R;
import com.jdroid.sample.android.imageloader.ImageLoaderActivity;
import com.jdroid.sample.android.ui.ads.AdsActivity;
import com.jdroid.sample.android.ui.analytics.AnalyticsActivity;
import com.jdroid.sample.android.ui.exceptions.ExceptionHandlingActivity;
import com.jdroid.sample.android.ui.loading.LoadingActivity;
import com.jdroid.sample.android.ui.maps.MapActivity;
import com.jdroid.sample.android.ui.notifications.NotificationsActivity;
import com.jdroid.sample.android.ui.toasts.ToastsActivity;

public enum HomeItem implements ActionItem {
	
	ADMOB(R.string.adMob, R.drawable.apps, AdsActivity.class),
	ANDROID_UNVERSAL_IMAGE_LOADER(R.string.universalImageLoader, R.drawable.photo, ImageLoaderActivity.class),
	EXCEPTION_HANDLING(R.string.exceptionHandling, R.drawable.bug, ExceptionHandlingActivity.class),
	GOOGLE_ANALYTCS(R.string.googleAnalytics, R.drawable.analytics, AnalyticsActivity.class),
	GCM(R.string.gcm, R.drawable.cloud, ImageLoaderActivity.class),
	GOOGLE_MAPS(R.string.googleMaps, R.drawable.map, MapActivity.class),
	GOOGLE_PLUS(R.string.googlePlus, R.drawable.google_plus, ImageLoaderActivity.class),
	IN_APP_BILLING(R.string.inAppBilling, R.drawable.shopping_cart, ImageLoaderActivity.class),
	LOADING(R.string.loading, R.drawable.refresh, LoadingActivity.class),
	MERGE_ADAPTER(R.string.mergeAdapter, R.drawable.list, ImageLoaderActivity.class),
	NOTIFICATIONS(R.string.notifications, R.drawable.notifications, NotificationsActivity.class),
	TOASTS(R.string.toasts, R.drawable.info, ToastsActivity.class);
	
	private Integer resourceId;
	private Integer iconId;
	private Class<? extends FragmentActivity> activityClass;
	
	private HomeItem(Integer resourceId, Integer iconId, Class<? extends FragmentActivity> activityClass) {
		this.resourceId = resourceId;
		this.iconId = iconId;
		this.activityClass = activityClass;
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#getNameResource()
	 */
	@Override
	public Integer getNameResource() {
		return resourceId;
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#getIconResource()
	 */
	@Override
	public Integer getIconResource() {
		return iconId;
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#startActivity(android.support.v4.app.FragmentActivity)
	 */
	@Override
	public void startActivity(FragmentActivity fragmentActivity) {
		ActivityLauncher.launchActivity(activityClass);
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#matchesActivity(android.support.v4.app.FragmentActivity)
	 */
	@Override
	public Boolean matchesActivity(FragmentActivity fragmentActivity) {
		return activityClass.equals(fragmentActivity.getClass());
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#createFragment(java.lang.Object)
	 */
	@Override
	public Fragment createFragment(Object args) {
		return null;
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#getName()
	 */
	@Override
	public String getName() {
		return name();
	}
	
	/**
	 * @see com.jdroid.android.ActionItem#getDescriptionResource()
	 */
	@Override
	public Integer getDescriptionResource() {
		return null;
	}
}