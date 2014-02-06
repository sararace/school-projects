package edu.ucsd.vis141a.snmillar.formart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class FormArtResultsActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_art_results);
		
		//((Button)findViewById(R.id.back)).setOnClickListener(this);
		WebView wv = ((WebView)findViewById(R.id.webView1));
		wv.setWebViewClient(new WebViewClient());
		wv.loadUrl("http://google.com/");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form_art_results, menu);
		return true;
	}
	
	public void onClick(View view){
		Intent intent = new Intent(FormArtResultsActivity.this, FormArtActivity.class);
		startActivity(intent);
	}
}