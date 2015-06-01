package mobi.porquenao.recents;

import android.os.Build;

import mobi.porquenao.recents.test.BaseInstrumentationTestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class RecentsHelperTest extends BaseInstrumentationTestCase {

    public void testIsAvailable() {
        assertThat(RecentsHelper.isAvailable()).isEqualTo(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH);
    }

    public void testIsLabelColorLight() {
        assertThat(RecentsHelper.isColorLight(0x000000)).isFalse();
        assertThat(RecentsHelper.isColorLight(0xFFFFFF)).isTrue();
    }

}
