package com.danielburgnerjr.cowsbells;

import android.app.Activity;
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
import com.google.android.gms.ads.MobileAds;

public class GameActivity extends Activity {
	
	private EditText etGuess;			// guess
	private String strAnswer;
	private int nGameCode;
	private int nGuess;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		MobileAds.initialize(this, "ca-app-pub-8379108590476103~5473406230");

		Intent intI = getIntent();
		nGameCode = Integer.parseInt(getIntent().getExtras().get("game_code").toString());
		strAnswer = setAnswer(nGameCode);

		etGuess  = (EditText)findViewById(R.id.txtGuess);
        nGuess = 0;
	}

	public String setAnswer(int nGC) {
	    String strA = "";

	    ArrayList alNumbers = new ArrayList();
	    for (int nI = 0; nI < 10; nI++) {
	        alNumbers.add(nI);
        }

        Collections.shuffle(alNumbers);
	    for (int nJ = 0; nJ < nGC; nJ++) {
	        strA += alNumbers.get(nJ);
        }
        return strA;
    }

	public void submit(View view) {
		String strSubmit = "Submit";
		String strGuess = etGuess.getText().toString();
		if (strGuess.length() != nGameCode) {
			Toast.makeText(getApplicationContext(), "Guess must be a " + nGameCode + " digit number", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), strSubmit + " " + strGuess, Toast.LENGTH_SHORT).show();
			compareAnswer(strGuess);
		}
	}

	public void newGame(View view) {
		String strNewGame = "New Game";
        strAnswer = setAnswer(nGameCode);
        etGuess.setText("");
        nGuess = 0;
		Toast.makeText(getApplicationContext(), strNewGame, Toast.LENGTH_SHORT).show();
	}

	public void compareAnswer(String strGuess) {
	    HashMap<Character, Integer> hmMap = new HashMap<Character, Integer>();
        LinearLayout llRowLayout = (LinearLayout) this.findViewById(R.id.row_layout1);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvNumber = new TextView(this);
        TextView tvGuess = new TextView(this);
        TextView tvCows = new TextView(this);
        TextView tvBells = new TextView(this);

	    int nCows = 0;      // numbers guessed right
	    int nBells = 0;     // numbers in exact location

	    // bells check
		for (int nI = 0; nI < nGameCode; nI++) {
			if (strGuess.charAt(nI) == strAnswer.charAt(nI)) {
				nBells++;
			} else {
				if (hmMap.containsKey(strAnswer.charAt(nI))) {
					int nFreq = hmMap.get(strAnswer.charAt(nI));
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
					if (hmMap.get(strGuess.charAt(nI)) == 1) {
						hmMap.remove(strGuess.charAt(nI));
					} else {
						int nFreq = hmMap.get(strGuess.charAt(nI));
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
        llRowLayout.addView(tvNumber);  // null pointer exception occurs here
        llRowLayout.addView(tvGuess);
        llRowLayout.addView(tvCows);
        llRowLayout.addView(tvBells);

        Toast.makeText(getApplicationContext(), "Cows: " + nCows + " Bells: " + nBells + " Guess: " + nGuess, Toast.LENGTH_SHORT).show();
        if (nBells == nGameCode) {
            youWin();
        }
    }

    public void youWin() {
        Toast.makeText(getApplicationContext(), "You Win in " + nGuess + " tries!", Toast.LENGTH_SHORT).show();
    }
}
