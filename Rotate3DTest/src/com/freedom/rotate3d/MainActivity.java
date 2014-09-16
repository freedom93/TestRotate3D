package com.freedom.rotate3d;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
/**
 * @Describle 
 * @author lifen
 */
public class MainActivity extends Activity {
	private ViewGroup layoutmain;
	private ViewGroup layoutnext;
	
	private Button btn_MainLast;
	private Button btn_MainNext;
	private Button btn_NextLast;
	private Button btn_NextNext;
	
	private Rotate3D lQuest1Animation;
	private Rotate3D lQuest2Animation;
	private Rotate3D rQuest1Animation;
	private Rotate3D rQuest2Animation;
	private int mCenterX = 240;		// 480x800 宽度的一半
	private int mCenterY = 400;		// 480x800 高度的一般
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAnimation();
        initMain();
	}
	
	
	
	private void initMain(){
        setContentView(R.layout.activity_main);

		layoutmain = (LinearLayout)findViewById(R.id.layout_main);
		btn_MainLast = (Button)findViewById(R.id.main_last);
		btn_MainNext = (Button)findViewById(R.id.main_next);
		
		btn_MainLast.setOnClickListener(listener);
		btn_MainNext.setOnClickListener(listener);
	}
	
	private void initNext(){
        setContentView(R.layout.next);

		layoutnext = (LinearLayout)findViewById(R.id.layout_next);
		btn_NextLast = (Button)findViewById(R.id.next_last);
		btn_NextNext = (Button)findViewById(R.id.next_next);
		
		btn_NextLast.setOnClickListener(listener);
		btn_NextNext.setOnClickListener(listener);
	}
	
	
	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_last:	// 向左
				layoutmain.startAnimation(lQuest1Animation);	// 向左旋转90°
				initNext();
				layoutnext.startAnimation(lQuest2Animation);	// 向左旋转90°
				break;
			case R.id.main_next:	// 向右
				layoutmain.startAnimation(rQuest1Animation);	// 向左旋转90°
				initNext();
				layoutnext.startAnimation(rQuest2Animation);	// 向右旋转90°
				break;
			case R.id.next_last:
				layoutnext.startAnimation(lQuest1Animation);
				initMain();
				layoutmain.startAnimation(lQuest2Animation);
				break;
			case R.id.next_next:
				layoutnext.startAnimation(rQuest1Animation);
				initMain();
				layoutmain.startAnimation(rQuest2Animation);
				break;
			}
		}
	};

	public void initAnimation() {
		// 获取旋转中心
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;
		
		int duration = 1000;
		//向左的question1旋转方向（从0度转到-90，参考系为水平方向为0度）
		lQuest1Animation = new Rotate3D(0, -90, mCenterX, mCenterY);	
		lQuest1Animation.setFillAfter(true);
		lQuest1Animation.setDuration(duration);

		//向左的question2旋转方向（从90度转到0，参考系为水平方向为0度）
		lQuest2Animation = new Rotate3D(90, 0, mCenterX, mCenterY);		
		lQuest2Animation.setFillAfter(true);
		lQuest2Animation.setDuration(duration);

	    //向右的question1旋转方向（从0度转到90，参考系为水平方向为0度）
		rQuest1Animation = new Rotate3D(0, 90, mCenterX, mCenterY);		
		rQuest1Animation.setFillAfter(true);
		rQuest1Animation.setDuration(duration);

		//向右的question2旋转方向（从-90度转到0，参考系为水平方向为0度）
		rQuest2Animation = new Rotate3D(-90, 0, mCenterX, mCenterY);	
		rQuest2Animation.setFillAfter(true);
		rQuest2Animation.setDuration(duration);
	}

}
