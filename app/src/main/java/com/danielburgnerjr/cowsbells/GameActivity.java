package com.danielburgnerjr.cowsbells;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends Activity {
	
	private EditText etGuess;			// guess
	private String strAnswer;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		int nGameCode;

		Intent intI = getIntent();
		nGameCode = Integer.parseInt(getIntent().getExtras().get("game_code").toString());
		switch (nGameCode) {
            case 4:
                strAnswer = setAnswer(nGameCode);
                Toast.makeText(getApplicationContext(), "Easy " + strAnswer, Toast.LENGTH_SHORT).show();
                break;
            case 5:
                strAnswer = setAnswer(nGameCode);
                Toast.makeText(getApplicationContext(), "Medium " + strAnswer, Toast.LENGTH_SHORT).show();
                break;
            case 6:
                strAnswer = setAnswer(nGameCode);
                Toast.makeText(getApplicationContext(), "Hard " + strAnswer, Toast.LENGTH_SHORT).show();
                break;
            case 7:
                strAnswer = setAnswer(nGameCode);
                Toast.makeText(getApplicationContext(), "Super Hard " + strAnswer, Toast.LENGTH_SHORT).show();
                break;
        }
		
		etGuess  = (EditText)findViewById(R.id.txtGuess);
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
	}

	public void newGame(View view) {
		String strNewGame = "New Game";
		Toast.makeText(getApplicationContext(), strNewGame, Toast.LENGTH_SHORT).show();	
	}

}
