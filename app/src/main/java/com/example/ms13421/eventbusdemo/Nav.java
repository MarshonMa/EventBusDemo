package com.example.ms13421.eventbusdemo;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by ms13421 on 2016/7/20.
 */
public class Nav {
    private static final String TAG = "Nav";

    public static Nav from(final Context context) {
        return new Nav(context);
    }

    public boolean toUri(final String uri) {
        if(TextUtils.isEmpty(uri)) return false;
        return toUri(Uri.parse(uri));
    }

    public boolean toUri(final Uri uri) {

        Log.d(TAG, uri.toString());

        final Intent intent = to(uri);

        for (;;) try {

            intent.setPackage(mContext.getPackageName());

            PackageManager pm = mContext.getPackageManager();

            final ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if(info == null) {
                throw new ActivityNotFoundException("No Activity found to handle " + intent);
            } else {
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            }

            mContext.startActivity(intent);
            return true;

        } catch (final ActivityNotFoundException e) {

            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startActivities(final Intent[] intents) {
        mContext.startActivities(intents);
    }

    private Intent to(final Uri uri) {
        mIntent.setData(uri);

        return mIntent;
    }

    private Nav(final Context context) {
        mContext = context;
        mIntent = new Intent(Intent.ACTION_VIEW);
    }

    private final Context mContext;
    private final Intent mIntent;
}
