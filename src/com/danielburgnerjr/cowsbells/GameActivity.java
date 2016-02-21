package com.danielburgnerjr.cowsbells;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class GameActivity extends Activity {
	
	private EditText etGuess;			// guess
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		Intent intI = getIntent();
		
		etGuess  = (EditText)findViewById(R.id.txtGuess);
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
