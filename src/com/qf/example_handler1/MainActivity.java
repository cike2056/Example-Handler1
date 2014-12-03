package com.qf.example_handler1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
/**主线程中加载耗时.
 * (没有控件,空白)卡死,或是崩溃.
 * @author uaige
 *
 */
public class MainActivity extends Activity {
	private ProgressBar pb;
	private TextView tx;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			msg.what=1;//标示
			pb.setProgress(msg.arg1);
			tx.setText(""+msg.arg1+"%");
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setTxProgress();
	}
	
	/**
	 * 初始化界面.
	 */
	private void initView() {
		// TODO Auto-generated method stub
		pb = (ProgressBar) findViewById(R.id.pb);
		tx =(TextView) findViewById(R.id.tx);
	}
	/**
	 * 设置Progress
	 */
	private void setTxProgress() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(1000);
//						pb.setProgress(i);
//						tx.setText(""+i+"%");
						Message message = handler.obtainMessage();
						message.arg1=i;/**内容*/
						message.obj="start";/**多类型内容*/
						message.what=1;/**标识*/
						handler.sendMessage(message);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
}
