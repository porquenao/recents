package mobi.porquenao.recents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import mobi.porquenao.recents.test.BaseInstrumentationTestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecentsTaskTest extends BaseInstrumentationTestCase {

    private RecentsTask mRecentsTask;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Recents.setDefault(null);
        mRecentsTask = new RecentsTask();
    }

    public void testLabel() {
        assertThat(mRecentsTask.label()).isNull();
        String label = "label";
        mRecentsTask.label(label);
        assertThat(mRecentsTask.label()).isSameAs(label);
    }

    public void testIcon() {
        assertThat(mRecentsTask.icon()).isNull();
        Bitmap icon = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        mRecentsTask.icon(icon);
        assertThat(mRecentsTask.icon()).isSameAs(icon);
    }

    public void testColor() {
        assertThat(mRecentsTask.label()).isNull();
        Integer color = 0xFFFFFFFF;
        mRecentsTask.color(color);
        assertThat(mRecentsTask.color()).isSameAs(color);
    }

    public void testLabelRes() {
        assertThat(mRecentsTask.labelRes()).isNull();
        Integer labelRes = android.R.string.cancel;
        mRecentsTask.labelRes(labelRes);
        assertThat(mRecentsTask.labelRes()).isSameAs(labelRes);
    }

    public void testIconRes() {
        assertThat(mRecentsTask.iconRes()).isNull();
        Integer iconRes = android.R.drawable.alert_dark_frame;
        mRecentsTask.iconRes(iconRes);
        assertThat(mRecentsTask.iconRes()).isSameAs(iconRes);
    }

    public void testColorRes() {
        assertThat(mRecentsTask.colorRes()).isNull();
        Integer colorRes = android.R.color.background_dark;
        mRecentsTask.colorRes(colorRes);
        assertThat(mRecentsTask.colorRes()).isSameAs(colorRes);
    }

    public void testFillWithResources() {
        mRecentsTask.labelRes(android.R.string.cancel)
                .iconRes(android.R.drawable.alert_dark_frame)
                .colorRes(android.R.color.background_dark);
        mRecentsTask.fillWithResources(getInstrumentation().getTargetContext());
        assertThat(mRecentsTask.label()).isNotNull();
        assertThat(mRecentsTask.icon()).isNotNull();
        assertThat(mRecentsTask.color()).isNotNull();
    }

    public void testFillWithDefault() {
        String label = "label";
        Bitmap icon = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        Integer color = 0xFFFFFFFF;
        RecentsTask recentsTask = new RecentsTask().label(label).icon(icon).color(color);
        mRecentsTask.fillWithDefault(getInstrumentation().getTargetContext(), recentsTask);
        assertThat(mRecentsTask.label()).isSameAs(label);
        assertThat(mRecentsTask.icon()).isSameAs(icon);
        assertThat(mRecentsTask.color()).isSameAs(color);
    }

    public void testFillWithDefault_Theme() {
        Context context = getInstrumentation().getTargetContext();
        context.setTheme(mobi.porquenao.recents.test.R.style.TestTheme);
        mRecentsTask.fillWithDefault(getInstrumentation().getTargetContext(), null);
        assertThat(mRecentsTask.color()).isEqualTo(0xFFABCDEF);
    }

    @SuppressLint("NewApi")
    public void testOn_Activity() {
        Context context = getInstrumentation().getTargetContext();
        context.setTheme(mobi.porquenao.recents.test.R.style.TestTheme);
        Activity activity = mock(Activity.class);
        when(activity.getTheme()).thenReturn(context.getTheme());
        mRecentsTask.on(activity);
        if (RecentsHelper.isAvailable()) {
            verify(activity).setTaskDescription(any(ActivityManager.TaskDescription.class));
        }
    }

    public void testOn_Application() {
        Application application = mock(Application.class);
        mRecentsTask.on(application);
        if (RecentsHelper.isAvailable()) {
            assertThat(Recents.getDefault()).isNotNull();
            verify(application).registerActivityLifecycleCallbacks(any(Application.ActivityLifecycleCallbacks.class));
        } else {
            assertThat(Recents.getDefault()).isNull();
        }
    }

}
