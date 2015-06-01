package mobi.porquenao.recents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.TypedValue;

public class RecentsTask {

    private String mLabel;
    private Bitmap mIcon;
    private Integer mColor;

    private Integer mLabelRes;
    private Integer mIconRes;
    private Integer mColorRes;

    public RecentsTask() {}

    public String label() {
        return mLabel;
    }

    public Bitmap icon() {
        return mIcon;
    }

    public Integer color() {
        return mColor;
    }

    public Integer labelRes() {
        return mLabelRes;
    }

    public Integer iconRes() {
        return mIconRes;
    }

    public Integer colorRes() {
        return mColorRes;
    }

    public RecentsTask label(String label) {
        mLabel = label;
        return this;
    }

    public RecentsTask icon(Bitmap icon) {
        mIcon = icon;
        return this;
    }

    public RecentsTask color(Integer color) {
        mColor = color;
        return this;
    }

    public RecentsTask labelRes(@StringRes Integer labelRes) {
        mLabelRes = labelRes;
        return this;
    }

    public RecentsTask iconRes(@DrawableRes Integer iconRes) {
        mIconRes = iconRes;
        return this;
    }

    public RecentsTask colorRes(@ColorRes Integer colorRes) {
        mColorRes = colorRes;
        return this;
    }

    public void fillWithResources(@NonNull Context context) {
        if (mLabel == null && mLabelRes != null) {
            mLabel = context.getResources().getString(mLabelRes);
            mLabelRes = null;
        }
        if (mIcon == null && mIconRes != null) {
            mIcon = BitmapFactory.decodeResource(context.getResources(), mIconRes);
            mIconRes = null;
        }
        if (mColor == null && mColorRes != null) {
            mColor = context.getResources().getColor(mColorRes);
            mColorRes = null;
        }
    }

    public void fillWithDefault(@NonNull Context context, @Nullable RecentsTask defaultRecentsTask) {
        if (defaultRecentsTask != null) {
            defaultRecentsTask.fillWithResources(context);
        }
        if (mLabel == null && defaultRecentsTask != null) {
            mLabel = defaultRecentsTask.label();
        }
        if (mIcon == null && defaultRecentsTask != null) {
            mIcon = defaultRecentsTask.icon();
        }
        if (mColor == null && defaultRecentsTask != null) {
            mColor = defaultRecentsTask.color();
        }
        if (mColor == null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            mColor = typedValue.data;
        }
    }

    public RecentsTask on(Activity activity) {
        if (RecentsHelper.isAvailable()) {
            fillWithResources(activity);
            fillWithDefault(activity, Recents.getDefault());
            Recents.set(activity, mLabel, mIcon, mColor);
        }
        return this;
    }

    @SuppressLint("NewApi")
    public RecentsTask on(Application application) {
        if (RecentsHelper.isAvailable()) {
            Recents.setDefault(this);
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    on(activity);
                }

                @Override
                public void onActivityDestroyed(Activity activity) {}

                @Override
                public void onActivityStarted(Activity activity) {}

                @Override
                public void onActivityResumed(Activity activity) {}

                @Override
                public void onActivityPaused(Activity activity) {}

                @Override
                public void onActivityStopped(Activity activity) {}

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            });
        }
        return this;
    }

}
