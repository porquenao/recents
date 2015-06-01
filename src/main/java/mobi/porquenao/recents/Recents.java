package mobi.porquenao.recents;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class Recents {

    private static RecentsTask sDefault;

    private Recents() {}

    public static RecentsTask label(String label) {
        return create(label, null, null, null, null, null);
    }

    public static RecentsTask icon(Bitmap icon) {
        return create(null, icon, null, null, null, null);
    }

    public static RecentsTask color(Integer color) {
        return create(null, null, color, null, null, null);
    }

    public static RecentsTask labelRes(@StringRes Integer labelRes) {
        return create(null, null, null, labelRes, null, null);
    }

    public static RecentsTask iconRes(@DrawableRes Integer iconRes) {
        return create(null, null, null, null, iconRes, null);
    }

    public static RecentsTask colorRes(@ColorRes Integer colorRes) {
        return create(null, null, null, null, null, colorRes);
    }

    public static RecentsTask on(Application application) {
        RecentsTask recentsTask = new RecentsTask();
        recentsTask.on(application);
        return recentsTask;
    }

    public static RecentsTask getDefault() {
        return sDefault;
    }

    public static void setDefault(RecentsTask recentsTask) {
        sDefault = recentsTask;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void set(Activity activity, String label, Bitmap icon, Integer color) {
        if (RecentsHelper.isAvailable()) {
            activity.setTaskDescription(new ActivityManager.TaskDescription(label, icon, color));
        }
    }

    private static RecentsTask create(String label, Bitmap icon, Integer color, Integer labelRes, Integer iconRes, Integer colorRes) {
        RecentsTask recentsTask = new RecentsTask();
        if (label != null) recentsTask.label(label);
        if (icon != null) recentsTask.icon(icon);
        if (color != null) recentsTask.color(color);
        if (labelRes != null) recentsTask.labelRes(labelRes);
        if (iconRes != null) recentsTask.iconRes(iconRes);
        if (colorRes != null) recentsTask.colorRes(colorRes);
        return recentsTask;
    }

}
