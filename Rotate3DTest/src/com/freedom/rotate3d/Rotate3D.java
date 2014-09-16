package com.freedom.rotate3d;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
/**
 * @Describle  Camera实现简单的 3D特效
 * @author lifen
 */
public class Rotate3D extends Animation {
	private float fromDegree;	// 旋转起始角度
	private float toDegree;		// 旋转终止角度
	private float mCenterX;		// 旋转中心x
	private float mCenterY;		// 旋转中心y
	private Camera mCamera;		//理解为2D图形系统中的视角或照相机的机位(透视的原理实现3D效果)

	public Rotate3D(float fromDegree, float toDegree, float centerX, float centerY) {
		this.fromDegree = fromDegree;
		this.toDegree = toDegree;
		this.mCenterX = centerX;
		this.mCenterY = centerY;

	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float FromDegree = fromDegree;
		float degrees = FromDegree + (toDegree - fromDegree) * interpolatedTime;	// 旋转角度（angle�?
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		// 计算对应当前转换矩阵，并将其复制到所提供的矩阵对象
		final Matrix matrix = t.getMatrix();

		// ratateXXX()适用于所有三个轴旋转变换
		if (degrees <= -76.0f) {
			degrees = -90.0f;
			mCamera.save();		//保存Camera状态
			mCamera.rotateY(degrees); // Y轴旋转
			mCamera.getMatrix(matrix);
			mCamera.restore();	//恢复保存的状态
		} else if (degrees >= 76.0f) {
			degrees = 90.0f;
			mCamera.save();
			mCamera.rotateY(degrees);
			mCamera.getMatrix(matrix);
			mCamera.restore();
		} else {
			mCamera.save();
			//机位的正向转动引起图片向屏幕里翻转
            //绕Y轴的转动，引起图片平面的旋转（3D效果） 
			//X引起图片向右移动0（正向朝右）
            //Y引起图片向上移动0（正向朝上）
            //Z引起图片缩小（正向指向自己）
			mCamera.translate(0, 0, centerX);	// 位移x (100, 100, centerX);//右上
			mCamera.rotateY(degrees);
			mCamera.translate(0, 0, -centerX);
			mCamera.getMatrix(matrix);
			mCamera.restore();
		}

		//移到中心点上   
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}



