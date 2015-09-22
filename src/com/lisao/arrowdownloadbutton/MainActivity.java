package com.lisao.arrowdownloadbutton;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity implements OnClickListener {

	private int count = 0;
	private ArrowDownloadButton button;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			button.setProgress(msg.arg1);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		button = (ArrowDownloadButton) findViewById(R.id.arrow_download_button);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if ((count % 2) == 0) {
			button.startAnimating();
			new Thread() {
				public void run() {
					int current = 0;
					while (true) {
						try {
							Thread.sleep(100);
							Message message = Message.obtain();
							message.arg1 = current++;
							handler.sendMessage(message);
							if (current > 100)
								break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
			}.start();
		} else {
			button.reset();
		}
		count++;
	}
}
