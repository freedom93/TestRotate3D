package com.freedom.imagerotatetest;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * 实现图片的翻转：picture左旋90°，picture2右旋90°
 * @author lifen
 */
public class MainActivity extends Activity {
	ImageView img;
	ViewGroup mContainer;
	private boolean which = false;// which用于区别当前图片的状态，为false表示点击前显示的是picture，为true表示是picture2
	private static final int IMAGE1 = R.drawable.picture;
	private static final int IMAGE2 = R.drawable.picture2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img = (ImageView) findViewById(R.id.frameLayout);
		mContainer = (ViewGroup) findViewById(R.id.image);
		img.setClickable(true);
		img.setFocusable(true);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击图片后的事件
				if (!which) {
					applyRotation(0, 0, -90);// 左旋90度
				} else {
					applyRotation(0, 0, 90);// 右旋90度
				}
			}
		});
	}

	/**
	 * 使用Rotate3d实现翻转
	 * @param position 
	 * @param start
	 * @param end
	 */
	private void applyRotation(int position, float start, float end) {
		// 获取FrameLayout的x、y值，这样图片在翻转的时候会以这个x、y值为中心翻转。
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;
		final Rotate3d rotation = new Rotate3d(start, end, centerX, centerY,
				310.0f, true);
		rotation.setDuration(1000); // 设置翻转的时间，以ms为单位
		rotation.setFillAfter(true);
		/**
		 * Interpolator插入器set方法参数说明
		   AccelerateInterpolator：动画从开始到结束，变化率是一个加速的过程。
		   DecelerateInterpolator：动画从开始到结束，变化率是一个减速的过程。
		   CycleInterpolator：动画从开始到结束，变化率是循环给定次数的正弦曲线。
		   AccelerateDecelerateInterpolator：动画从开始到结束，变化率是先加速后减速的过程。
		   LinearInterpolator：动画从开始到结束，变化率是线性变化。
		 */
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView());
		mContainer.startAnimation(rotation); // 开始翻转前90度
	}

	/**
	 * 用于监听前90度翻转完成
	 */
	private final class DisplayNextView implements Animation.AnimationListener {
		private DisplayNextView() {
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			// 前90度翻转完成后，根据图片的状态翻转剩下的90度
			if (!which) {
				img.setImageResource(IMAGE2);
				mContainer.post(new SwapViews(0));
			} else {
				img.setImageResource(IMAGE1);
				mContainer.post(new SwapViews(1));
			}
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * 用于翻转剩下的90度
	 */
	private final class SwapViews implements Runnable {
		private final int mdirection;

		// 用一个方向变量来指明剩下的90度应该怎么翻转。
		public SwapViews(int direction) {
			mdirection = direction;
		}

		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3d rotation;
			if (mdirection == 0) {
				rotation = new Rotate3d(90, 0, centerX, centerY, 310.0f, false);
				which = true; // 待翻转完成后，修改图片状态
			} else {
				rotation = new Rotate3d(-90, 0, centerX, centerY, 310.0f, false);
				which = false;
			}
			rotation.setDuration(1000);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			mContainer.startAnimation(rotation); // 开始翻转余下的90度
		}
	}
}
