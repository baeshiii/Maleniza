package com.jarMaleniza.maleniza;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class jarMALENIZA extends AppCompatActivity {

    private TextView resultNumber1, resultNumber2, resultNumber3, resultText, balanceText, multiplierText;
    private EditText bet1, bet2, bet3, beteditText;
    private Button betButton, playButton, resetButton;
    private Button betAndPlayButton; // Updated button variable

    private boolean betButtonClicked = false;
    private boolean resetButtonClicked = false;
    private boolean isBetting = true;

    private int balance = 1000;
    private int multiplier = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultNumber1 = findViewById(R.id.result_number_1);
        resultNumber2 = findViewById(R.id.result_number_2);
        resultNumber3 = findViewById(R.id.result_number_3);
        resultText = findViewById(R.id.result_text);
        balanceText = findViewById(R.id.balance_text);
        multiplierText = findViewById(R.id.multiplier_text);

        bet1 = findViewById(R.id.bet1);
        bet2 = findViewById(R.id.bet2);
        bet3 = findViewById(R.id.bet3);
        beteditText = findViewById(R.id.betEditText);

//        betButton = findViewById(R.id.bet_button);
//        playButton = findViewById(R.id.play_button);


        resetButton = findViewById(R.id.reset_button);

        betAndPlayButton = findViewById(R.id.bet_and_play_button);

        betAndPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBetting) {
                    if (!betButtonClicked) {
                        clearBets();
                        betButtonClicked = true;
                    } else {
                        showMessage("You've already placed your bet.");
                    }
                } else {
                    if (checkBets() && !bet1.getText().toString().isEmpty() && !bet2.getText().toString().isEmpty() && !bet3.getText().toString().isEmpty()) {
                        int betAmount = Integer.parseInt(beteditText.getText().toString());
                        if (betAmount <= balance) {
                            int[] results = generateResults();
                            displayResults(results);
                            checkWin(results, betAmount);
                        } else {
                            showMessage("Entered amount is more than the current balance.");
                        }
                    } else {
                        showMessage("Please enter the bet amount in all fields.");
                    }
                }

                isBetting = !isBetting;
                betAndPlayButton.setText(isBetting ? "BET" : "PLAY");

                if (!isBetting) {
//                    playButton.setVisibility(View.VISIBLE);
//                    betButton.setVisibility(View.VISIBLE);
                }
                bet1.setText("");
                bet2.setText("");
                bet3.setText("");

            }
        });





        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void clearBets() {
        bet1.setText("");
        bet2.setText("");
        bet3.setText("");
    }

    private boolean checkBets() {
        return !beteditText.getText().toString().isEmpty();
    }

    private int[] generateResults() {
        Random random = new Random();
        int[] results = new int[3];
        for (int i = 0; i < 3; i++) {
            results[i] = random.nextInt(9) + 1;
        }
        return results;
    }

    private void displayResults(int[] results) {
        resultNumber1.setText("Result#1: " + results[0]);
        resultNumber2.setText("Result#2: " + results[1]);
        resultNumber3.setText("Result#3: " + results[2]);
    }

    private void checkWin(int[] results, int betAmount) {
        if (results[0] == results[1] && results[1] == results[2]) {
            int winAmount = betAmount * multiplier;
            balance += winAmount;
            multiplier++;
            showMessage("You WIN! Your win amount is " + winAmount);
        } else {
            balance -= betAmount;
            multiplier = 2;
            showMessage("You LOSE!");
        }
        updateBalanceAndMultiplier();
    }

    private void updateBalanceAndMultiplier() {
        balanceText.setText("Balance: " + balance);
        multiplierText.setText("Multiplier: " + multiplier + "x");
        if (balance == 0) {
            resetButton.setVisibility(View.VISIBLE);
        }
    }

    private void resetGame() {
        balance = 1000;
        multiplier = 2;
        clearBets();
        updateBalanceAndMultiplier();
        resultNumber1.setText("Number 1: ");
        resultNumber2.setText("Number 2: ");
        resultNumber3.setText("Number 3: ");
        resultText.setText("");
//        playButton.setVisibility(View.VISIBLE);
//        resetButton.setVisibility(View.VISIBLE);
        bet1.setText("");
        bet2.setText("");
        bet3.setText("");
        beteditText.setText("");
    }
    private void showMessage(String message) {
        resultText.setText(message);
    }
}
