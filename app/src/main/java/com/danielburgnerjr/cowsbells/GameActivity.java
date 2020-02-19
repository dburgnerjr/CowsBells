package com.danielburgnerjr.cowsbells;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class GameActivity extends Activity {
	
	private EditText etGuess;			// guess
	private String strAnswer;
	private int nGameCode;
	private int nGuess;
	AdView mAdView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		MobileAds.initialize(this, String.valueOf(R.string.admob_app_id));
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		Intent intI = getIntent();
		if (intI.getExtras() != null)
			nGameCode = Integer.parseInt(Objects.requireNonNull(intI.getExtras().get("game_code")).toString());
		strAnswer = setAnswer(nGameCode);

		etGuess = findViewById(R.id.txtGuess);
        nGuess = 0;
	}

	public String setAnswer(int nGC) {
	    StringBuilder strA = new StringBuilder();

	    List<Integer> alNumbers = new ArrayList<>();
	    for (int nI = 0; nI < 10; nI++)
	    	alNumbers.add(nI);

        Collections.shuffle(alNumbers);
	    for (int nJ = 0; nJ < nGC; nJ++) {
	        strA.append(alNumbers.get(nJ));
        }
        return strA.toString();
    }

	public void submit(View view) {
		String strGuess = etGuess.getText().toString();
		if (strGuess.length() != nGameCode) {
			Toast.makeText(getApplicationContext(), "Guess must be a " + nGameCode + " digit number", Toast.LENGTH_SHORT).show();
		} else {
			compareAnswer(strGuess);
			etGuess.setText("");
		}
	}

	public void newGame(View view) {
		String strNewGame = "New Game";
        strAnswer = setAnswer(nGameCode);
        etGuess.setText("");
        nGuess = 0;
		LinearLayout ll = findViewById(R.id.tableGuesses);
		ll.removeAllViews();
		Toast.makeText(getApplicationContext(), strNewGame, Toast.LENGTH_SHORT).show();
	}

	public void compareAnswer(String strGuess) {
	    HashMap<Character, Integer> hmMap = new HashMap<>();
        LinearLayout llTableGuesses = this.findViewById(R.id.tableGuesses);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.weight = 1;
        LinearLayout llNewRowLayout = new LinearLayout(this);
        llNewRowLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView tvNumber = new TextView(this);
        TextView tvGuess = new TextView(this);
        TextView tvCows = new TextView(this);
        TextView tvBells = new TextView(this);

	    int nCows = 0;      // numbers guessed right
	    int nBells = 0;     // numbers in exact location
		int nFreq;

	    // bells check
		for (int nI = 0; nI < nGameCode; nI++) {
			if (strGuess.charAt(nI) == strAnswer.charAt(nI)) {
				nBells++;
			} else {
				if (hmMap.containsKey(strAnswer.charAt(nI))) {
					nFreq = Objects.requireNonNull(hmMap.get(strAnswer.charAt(nI)));
					nFreq++;
					hmMap.put(strAnswer.charAt(nI), nFreq);
				} else {
					hmMap.put(strAnswer.charAt(nI), 1);
				}
			}
        }

        // cows check
		for (int nI = 0; nI < nGameCode; nI++) {
			if (strGuess.charAt(nI) != strAnswer.charAt(nI)) {
				if (hmMap.containsKey(strGuess.charAt(nI))) {
					nCows++;
					if (Objects.requireNonNull(hmMap.get(strGuess.charAt(nI))) == 1) {
						hmMap.remove(strGuess.charAt(nI));
					} else {
						nFreq = Objects.requireNonNull(hmMap.get(strGuess.charAt(nI)));
						nFreq--;
						hmMap.put(strGuess.charAt(nI), nFreq);
					}
				}
			}
		}
		nGuess++;

        tvNumber.setLayoutParams(lparams);
        tvNumber.setText(String.valueOf(nGuess));
        tvGuess.setLayoutParams(lparams);
        tvGuess.setText(strGuess);
        tvCows.setLayoutParams(lparams);
        tvCows.setText(String.valueOf(nCows));
        tvBells.setLayoutParams(lparams);
        tvBells.setText(String.valueOf(nBells));
        llNewRowLayout.addView(tvNumber);
        llNewRowLayout.addView(tvGuess);
        llNewRowLayout.addView(tvCows);
        llNewRowLayout.addView(tvBells);
        llTableGuesses.addView(llNewRowLayout);

        if (nBells == nGameCode) {
            youWin();
        }
    }

    public void youWin() {
		AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
		alertDialog.setTitle("You Win!");
		alertDialog.setMessage("You Win in " + nGuess + " tries!");
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				(dialog, which) -> {
					Intent intB = new Intent(GameActivity.this, MainActivity.class);
					startActivity(intB);
					finish();
				});
		alertDialog.show();
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
    }
}
