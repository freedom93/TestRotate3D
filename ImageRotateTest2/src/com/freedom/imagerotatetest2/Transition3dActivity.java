package com.freedom.imagerotatetest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * listView预览图片（3d翻转）
 * @author lifen
 *
 */
public class Transition3dActivity extends Activity implements   
        AdapterView.OnItemClickListener, View.OnClickListener {    
    //图片列表     
    private ListView mPhotosList;    
    private ViewGroup mContainer;    
    private ImageView mImageView;    
   
    // 图片的名字，用于显示在list中     
    private static final String[] PHOTOS_NAMES = new String[] {    
            "picture1",    
            "picture2",    
            "picture3",    
            "picture4",    
            "picture5",    
            "picture6"   
    };    
   
    // 资源id     
    private static final int[] PIC_RESOURCES = new int[] {    
            R.drawable.picture1,    
            R.drawable.picture2,    
            R.drawable.picture3,    
            R.drawable.picture4,    
            R.drawable.picture5,    
            R.drawable.picture6    
    };    
   
    @Override   
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
   
        setContentView(R.layout.activity_main);    
   
        mPhotosList = (ListView) findViewById(android.R.id.list);    
        mImageView = (ImageView) findViewById(R.id.picture);    
        mContainer = (ViewGroup) findViewById(R.id.container);    
   
        // 准备ListView     
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,    
                android.R.layout.simple_list_item_1, PHOTOS_NAMES);    
   
        mPhotosList.setAdapter(adapter);    
        mPhotosList.setOnItemClickListener(this);    
   
        // 准备ImageView     
        mImageView.setClickable(true);    
        mImageView.setFocusable(true);    
        mImageView.setOnClickListener(this);    
   
        //设置需要保存缓存     
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);    
    }    
   
    /**     
     * 使用Rotate3d实现翻转
     * @param position 1-6对应picture1-6；-1对应list
     * @param start 翻转起始角度   
     * @param end 翻转终止角度   
     */   
    private void applyRotation(int position, float start, float end) {    
        // 计算中心点     
        final float centerX = mContainer.getWidth() / 2.0f;    
        final float centerY = mContainer.getHeight() / 2.0f;    
     
        final Rotate3dAnimation rotation =    
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);    
        rotation.setDuration(500);    
        rotation.setFillAfter(true);    
        rotation.setInterpolator(new AccelerateInterpolator());    
        //设置监听     
        rotation.setAnimationListener(new DisplayNextView(position));    
   
        mContainer.startAnimation(rotation);    
    }    
   
    public void onItemClick(AdapterView parent, View v, int position, long id) {    
        // 设置ImageView     
        mImageView.setImageResource(PIC_RESOURCES[position]);    
        applyRotation(position, 0, 90);    
    }    
    //点击图像时，返回listview     
    public void onClick(View v) {    
        applyRotation(-1, 180, 90);    
    }    
   

	/**
	 * 用于监听前90度翻转完成
	 */ 
    private final class DisplayNextView implements Animation.AnimationListener {    
        private final int mPosition;    
   
        private DisplayNextView(int position) {    
            mPosition = position;    
        }    
   
        public void onAnimationStart(Animation animation) {    
        }    
        //动画结束     
        public void onAnimationEnd(Animation animation) {    
            mContainer.post(new SwapViews(mPosition));    
        }    
   
        public void onAnimationRepeat(Animation animation) {    
        }    
    }    
   
    /**
	 * 用于翻转剩下的90度
	 */ 
    private final class SwapViews implements Runnable {    
        private final int mPosition;    
   
        public SwapViews(int position) {    
            mPosition = position;    
        }    
   
        public void run() {    
            final float centerX = mContainer.getWidth() / 2.0f;    
            final float centerY = mContainer.getHeight() / 2.0f;    
            Rotate3dAnimation rotation;    
                
            if (mPosition > -1) {    
                //显示ImageView     
                mPhotosList.setVisibility(View.GONE);    
                mImageView.setVisibility(View.VISIBLE);    
                mImageView.requestFocus();    
   
                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);    
            } else {    
                //返回listview     
                mImageView.setVisibility(View.GONE);    
                mPhotosList.setVisibility(View.VISIBLE);    
                mPhotosList.requestFocus();    
   
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);    
            }    
   
            rotation.setDuration(500);    
            rotation.setFillAfter(true);    
            rotation.setInterpolator(new DecelerateInterpolator());    
            //开始动画     
            mContainer.startAnimation(rotation);    
        }    
    }    
   
}   