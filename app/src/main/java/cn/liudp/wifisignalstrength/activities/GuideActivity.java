package cn.liudp.wifisignalstrength.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;

import java.util.Random;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.liudp.wifisignalstrength.MainActivity;
import cn.liudp.wifisignalstrength.base.BaseActivity;
import cn.liudp.wifisignalstrength.R;

/**
 * @author dongpoliu on 2018-03-29.
 */

public class GuideActivity extends BaseActivity{

    private boolean isFromSplash;
    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    private static final int[] images = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
    };
    @Override
    protected int getLayout() {
        System.out.println("WiFiSignalStrength Guide getLayout");
        return R.layout.activity_guide;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        System.out.println("WiFiSignalStrength Guide");
        isFromSplash = getIntent().getBooleanExtra("isFromSplash", true);
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        Random random = new Random(SystemClock.elapsedRealtime());
        mBackgroundBanner.setData(localImageSize,
                ImageView.ScaleType.CENTER_CROP,
                images[random.nextInt(images.length)]);

        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                close();

            }
        });
    }

    private void close() {
        if (isFromSplash) {
            ActivityUtils.startActivity(MainActivity.class);
            ActivityCompat.finishAfterTransition(GuideActivity.this);
        } else {
            ActivityCompat.finishAfterTransition(GuideActivity.this);
        }
    }

    @Override
    public void onBackPressedSupport() {
        close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
