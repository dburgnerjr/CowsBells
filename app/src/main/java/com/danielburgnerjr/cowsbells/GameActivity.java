package com.danielburgnerjr.cowsbells;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Collections;
import java.util.ArrayList;

public class GameActivity extends Activity {
	
	private EditText etGuess;			// guess
	private String strAnswer;
	private int nGameCode;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		Intent intI = getIntent();
		nGameCode = Integer.parseInt(getIntent().getExtras().get("game_code").toString());
		strAnswer = setAnswer(nGameCode);

		etGuess  = (EditText)findViewById(R.id.txtGuess);
        Toast.makeText(getApplicationContext(), strAnswer, Toast.LENGTH_SHORT).show();
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
		Toast.makeText(getApplicationContext(), strSubmit + " " + strGuess, Toast.LENGTH_SHORT).show();
		compareAnswer(strGuess);
	}

	public void newGame(View view) {
		String strNewGame = "New Game";
        strAnswer = setAnswer(nGameCode);
		Toast.makeText(getApplicationContext(), strNewGame + " " + strAnswer, Toast.LENGTH_SHORT).show();
	}

	public void compareAnswer(String strGuess) {
	    String[] strGuessParts = strGuess.split("");
	    String[] strAnswerParts = strAnswer.split("");
	    int nCows = 0;      // numbers guessed right
	    int nBells = 0;     // numbers in exact location

	    for (int nI = 0; nI < nGameCode; nI++) {
            for (int nJ = 0; nJ < nGameCode; nJ++) {
                if (strGuessParts[nI].equals(strAnswerParts[nJ])) {
                    nCows++;
                    if (nI == nJ) {
                        nBells++;
                    }
                }
            }
        }
        Toast.makeText(getApplicationContext(), "Cows: " + nCows + " Bells: " + nBells, Toast.LENGTH_SHORT).show();
    }
}
