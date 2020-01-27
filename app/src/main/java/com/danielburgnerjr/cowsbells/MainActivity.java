package com.danielburgnerjr.cowsbells;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {

	private final int N_EASY = 4;
	private final int N_MEDIUM = 5;
	private final int N_HARD = 6;
	private final int N_SUPER_HARD = 7;
	AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MobileAds.initialize(this, String.valueOf(R.string.admob_app_id));
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		final Button btnEasy = findViewById(R.id.btnEasy);
		btnEasy.setOnClickListener(new OnClickListener() {
            int nGameCode;
			public void onClick(View view) {
			    nGameCode = N_EASY;
			    Intent intA = new Intent(MainActivity.this, GameActivity.class);
			    intA.putExtra("game_code", nGameCode);
			    startActivity(intA);
			}
		});

		final Button btnMedium = findViewById(R.id.btnMedium);
		btnMedium.setOnClickListener(new OnClickListener() {
            int nGameCode;
			public void onClick(View view) {
                nGameCode = N_MEDIUM;
			    Intent intA = new Intent(MainActivity.this, GameActivity.class);
                intA.putExtra("game_code", nGameCode);
                startActivity(intA);
			}
		});

		final Button btnHard = findViewById(R.id.btnHard);
		btnHard.setOnClickListener(new OnClickListener() {
            int nGameCode;
			public void onClick(View view) {
                nGameCode = N_HARD;
                Intent intA = new Intent(MainActivity.this, GameActivity.class);
                intA.putExtra("game_code", nGameCode);
                startActivity(intA);
			}
		});

		final Button btnSuperHard = findViewById(R.id.btnSuperHard);
		btnSuperHard.setOnClickListener(new OnClickListener() {
            int nGameCode;
            public void onClick(View view) {
                nGameCode = N_SUPER_HARD;
                Intent intA = new Intent(MainActivity.this, GameActivity.class);
                intA.putExtra("game_code", nGameCode);
                startActivity(intA);
			}
		});

		final Button btnHowToPlay = findViewById(R.id.btnHowToPlay);
		btnHowToPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intA = new Intent(MainActivity.this, HowToPlayActivity.class);
				startActivity(intA);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
