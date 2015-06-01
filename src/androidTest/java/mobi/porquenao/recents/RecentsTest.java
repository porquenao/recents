package mobi.porquenao.recents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;

import mobi.porquenao.recents.test.BaseInstrumentationTestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RecentsTest extends BaseInstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Recents.setDefault(null);
    }

    public void testLabel() {
        String label = "label";
        RecentsTask task1 = Recents.label(label);
        RecentsTask task2 = new RecentsTask().label(label);
        assertThat(task1.label()).isNotNull();
        assertThat(task1.label()).isSameAs(task2.label());
    }

    public void testIcon() {
        Bitmap icon = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        RecentsTask task1 = Recents.icon(icon);
        RecentsTask task2 = new RecentsTask().icon(icon);
        assertThat(task1.icon()).isNotNull();
        assertThat(task1.icon()).isSameAs(task2.icon());
    }

    public void testColor() {
        Integer color = 0xFFFFFFFF;
        RecentsTask task1 = Recents.color(color);
        RecentsTask task2 = new RecentsTask().color(color);
        assertThat(task1.color()).isNotNull();
        assertThat(task1.color()).isSameAs(task2.color());
    }

    public void testLabelRes() {
        Integer labelRes = android.R.string.cancel;
        RecentsTask task1 = Recents.labelRes(labelRes);
        RecentsTask task2 = new RecentsTask().labelRes(labelRes);
        assertThat(task1.labelRes()).isNotNull();
        assertThat(task1.labelRes()).isSameAs(task2.labelRes());
    }

    public void testIconRes() {
        Integer iconRes = android.R.drawable.alert_dark_frame;
        RecentsTask task1 = Recents.iconRes(iconRes);
        RecentsTask task2 = new RecentsTask().iconRes(iconRes);
        assertThat(task1.iconRes()).isNotNull();
        assertThat(task1.iconRes()).isSameAs(task2.iconRes());
    }

    public void testColorRes() {
        Integer colorRes = android.R.color.background_dark;
        RecentsTask task1 = Recents.colorRes(colorRes);
        RecentsTask task2 = new RecentsTask().colorRes(colorRes);
        assertThat(task1.colorRes()).isNotNull();
        assertThat(task1.colorRes()).isSameAs(task2.colorRes());
    }

    public void testOn() {
        Application application = (Application) getInstrumentation().getTargetContext().getApplicationContext();
        assertThat(Recents.getDefault()).isNull();
        RecentsTask recentsTask = Recents.on(application);
        if (RecentsHelper.isAvailable()) {
            assertThat(Recents.getDefault()).isSameAs(recentsTask);
        } else {
            assertThat(Recents.getDefault()).isNull();
        }
    }

    public void testGetDefault() {
        assertThat(Recents.getDefault()).isNull();
    }

    public void testSetDefault() {
        RecentsTask recentsTask = new RecentsTask();
        Recents.setDefault(recentsTask);
        assertThat(Recents.getDefault()).isSameAs(recentsTask);
    }

    @SuppressLint("NewApi")
    public void testSet() {
        Activity activity = mock(Activity.class);
        String label = "label";
        Bitmap icon = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        Integer color = 0xFFFFFFFF;
        Recents.set(activity, label, icon, color);
        if (RecentsHelper.isAvailable()) {
            verify(activity).setTaskDescription(any(ActivityManager.TaskDescription.class));
        }
    }

}
