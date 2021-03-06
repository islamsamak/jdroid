package com.jdroid.android.ad;

import java.util.Random;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jdroid.android.AbstractApplication;
import com.jdroid.android.R;
import com.jdroid.android.utils.GooglePlayUtils;
import com.jdroid.android.utils.SharedPreferencesHelper;

public class RateAppView extends RelativeLayout {
	
	private static final String RATE_ME_CLICK_TIMESTAMP = "rateMeClickTimestamp";
	
	public RateAppView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public RateAppView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public RateAppView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(final Context context) {
		
		LayoutInflater.from(context).inflate(R.layout.rate_app_view, this, true);
		
		((TextView)findViewById(R.id.rateAppTitle)).setText(context.getString(R.string.rateMeTitle,
			context.getString(R.string.appName)));
		
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rateMeClicked();
				GooglePlayUtils.launchAppDetails(context);
				AbstractApplication.get().getAnalyticsSender().trackRateMeBannerClicked();
			}
			
		});
	}
	
	public static void rateMeClicked() {
		SharedPreferencesHelper.get().savePreference(RATE_ME_CLICK_TIMESTAMP, System.currentTimeMillis());
	}
	
	public static Boolean displayRateMe() {
		Boolean enoughDaysSinceFirstSession = AbstractApplication.get().getAppContext().getDaysSinceFirstSession() > 7;
		return new Random().nextBoolean() && !SharedPreferencesHelper.get().hasPreference(RATE_ME_CLICK_TIMESTAMP)
				&& enoughDaysSinceFirstSession;
	}
}
