package com.freedom.rotate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	ImageView imageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button)findViewById(R.id.rotate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Matrix mMatrix = new Matrix();
				imageView = (ImageView) findViewById(R.id.myimage);
				imageView.setVisibility(View.VISIBLE);

				Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.test)).getBitmap();

				Camera camera = new Camera();
				camera.save();
				camera.rotateY(50f);
				// camera.rotateX(50f);
				// camera.rotateZ(50f);
				camera.getMatrix(mMatrix);
				camera.restore();

				Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
						bmp.getHeight(), mMatrix, true);

				imageView.setImageBitmap(bm);
			}
		});
		
	}
}