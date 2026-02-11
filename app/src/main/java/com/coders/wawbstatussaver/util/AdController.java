package com.coders.wawbstatussaver.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.coders.wawbstatussaver.R;

public class AdController {

    public static boolean isLoadIronSourceAd = false;

    public static void initAd(Context context) {
        if (SharedPrefs.isAdsRemoved(context)) return;
        MobileAds.initialize(context, initializationStatus -> {});
    }

    static AdView gadView;
    public static void loadBannerAd(Context context, LinearLayout adContainer) {
        if (SharedPrefs.isAdsRemoved(context)) {
            adContainer.removeAllViews();
            return;
        }
        gadView = new AdView(context);
        gadView.setAdUnitId(context.getString(R.string.admob_banner_id));
        adContainer.removeAllViews();
        adContainer.addView(gadView);
        loadBanner(context);
    }

    static void loadBanner(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize((Activity) context);
        gadView.setAdSize(adSize);
        gadView.loadAd(adRequest);
    }

    static AdSize getAdSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    static InterstitialAd mInterstitialAd;

    public static void loadInterAd(Context context) {
        if (SharedPrefs.isAdsRemoved(context)) return;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getString(R.string.admob_interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });
    }

    public static void showInterAd(final Activity context, final Intent intent, final int requestCode) {
        if (SharedPrefs.isAdsRemoved(context)) {
            startActivity(context, intent, requestCode);
            return;
        }
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    loadInterAd(context);
                    startActivity(context, intent, requestCode);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    startActivity(context, intent, requestCode);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mInterstitialAd = null;
                }
            });
            mInterstitialAd.show(context);
        } else {
            startActivity(context, intent, requestCode);
        }
    }

    static void startActivity(Activity context, Intent intent, int requestCode) {
        if (intent != null) {
            context.startActivityForResult(intent, requestCode);
        }
    }

    public static void inItIron(Activity activity) {
    }

    public static void destroyIron() {
    }

    public static void ironBanner(Activity activity, LinearLayout container) {
    }

    public static void ironShowInterstitial(Activity activity, Intent intent, int i) {
    }
}
