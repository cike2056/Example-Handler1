package com.qf.example_handler1;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

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
			if(msg.what==1)//标示
			{tx.setText(msg.obj.toString());}
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
					HttpClient client = new DefaultHttpClient();
					HttpParams params = client.getParams();
					HttpGet httpGet = new HttpGet("http://sns.maimaicha.com/api?apikey=b4f4ee"
							+ "31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=0&rows=15");
					HttpConnectionParams.setConnectionTimeout(params, 5000);
					try {
						HttpResponse httpResponse = client.execute(httpGet);
						if(httpResponse!=null&&httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							HttpEntity entity = httpResponse.getEntity();
							String string = EntityUtils.toString(entity);
							handler.sendMessage(handler.obtainMessage(1, string));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
}
