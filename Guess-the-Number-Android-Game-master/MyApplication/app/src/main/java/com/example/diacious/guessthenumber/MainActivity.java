
//Copyright (Yerucham)
//Bugs:None known
//Version 1.0
package com.example.diacious.guessthenumber;


import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
    private TextView displayTextView;
    private TextView hintsLeftTextView;
    private TextView trialsLeftTextView;
    private TextView levelTextView;
    private TextView scoreTextView;
    private Button hintsButton;
    private Button startButton;
    private boolean start = false;
    private boolean clear = true;
    private boolean inGame;
    private int currentLevel = 1;
    private int numberToBeGuessed;
    private int trialsLeft;
    private int hintsLeft;
    private final int MAX_SCORE_PER_LEVEL = 100;
    private int score = MAX_SCORE_PER_LEVEL;
    private int lowerLimitForHint;
    private int upperLimitForHint;
    private Button deleteB;
    private final String DISPLAY_BACKUP = "display";
    private final String GUESS_NUM_BACKUP = "number_to_be_guessed";
    private final String LEVEL_BACKUP = "level";
    private final String START_BTN_TEXT = "start_btn";
    private final String HINTS_LEFT_BACKUP = "hints_left";
    private final String TRIALS_LEFT_BACKUP = "trials_left";
    private final String UP_LIM_4_HINTS = "upper_limits_for_hints";
    private final String LOW_LIM_4_HINTS = "lower_limits_for_hints";
    private final String START_BOOL_BACKUP = "start";
    private final String CLEAR_BOOL_BACKUP = "clear";
    private final String INGAME_BOOL_BACKUP = "ingame";
    private final String TRIALS_LEFT_TV_COLOR_BACKUP = "color_of_trials_left_tv";
    private final String SCORE_BACKUP = "score";
    private final String SCORE_TV_ON = "score_tv_on";
    private final int LEVEL_ONE_MAX = 100;
    private final int LEVEL_TWO_MAX = 500;
    private final int LEVEL_THREE_MAX = 1000;
    private final int LEVEL_FOUR_MAX = 5000;
    private final int LEVEL_FIVE_MAX = 10000;
    private final int LEVEL_SIX_MAX = 50000;
    private final int LEVEL_SEVEN_MAX = 100000;
    private final int LEVEL_EIGHT_MAX = 500000;
    private final int MAX_LEVEL = 8;
    private final int EXTRA_GUESS = 1;
    private final int SCORE_DROP_BY_TRIAL = 3;
    private final int SCORE_DROP_BY_HINT = 2;
    private final int NUM_HINTS_FOR_LEVEL_3_AND_ABOVE = 3;
    private int trials_left_color;
    private boolean score_Tv_On;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTextView = (TextView)findViewById(R.id.displayTv);
        hintsLeftTextView = (TextView) findViewById(R.id.hintsLeftTv);
        trialsLeftTextView = (TextView)findViewById(R.id.trials_leftTv);
        levelTextView = (TextView) findViewById(R.id.levelTv);
        scoreTextView = (TextView) findViewById(R.id.scoreTv);
        hintsButton = (Button)findViewById(R.id.hintButton);
        hintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inGame||!start)
                    return;
                giveHint();
            }
        });
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startGame(currentLevel);

            }
        });
        deleteB = (Button) findViewById(R.id.deleteB);
        try {
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(DISPLAY_BACKUP))
                    displayTextView.setText(savedInstanceState.getString(DISPLAY_BACKUP));
                if (savedInstanceState.containsKey(GUESS_NUM_BACKUP))
                    numberToBeGuessed = savedInstanceState.getInt(GUESS_NUM_BACKUP);
                if (savedInstanceState.containsKey(LEVEL_BACKUP)) {
                    currentLevel = savedInstanceState.getInt(LEVEL_BACKUP);
                    switch (currentLevel) {
                        case 1:
                            levelTextView.setText(getString(R.string.level1Tag));
                            break;
                        case 2:
                            levelTextView.setText(getString(R.string.level2Tag));
                            break;
                        case 3:
                            levelTextView.setText(getString(R.string.level3Tag));
                            break;
                        case 4:levelTextView.setText(getString(R.string.level4Tag));
                            break;
                        case 5:levelTextView.setText(getString(R.string.level5Tag));
                            break;
                        case 6:levelTextView.setText(getString(R.string.level6Tag));
                            break;
                        case 7:levelTextView.setText(getString(R.string.level7Tag));
                            break;
                        case 8:levelTextView.setText(getString(R.string.level8Tag));
                            break;
                    }
                }
                if (savedInstanceState.containsKey(START_BTN_TEXT))
                    startButton.setText(savedInstanceState.getString(START_BTN_TEXT));
                if (savedInstanceState.containsKey(HINTS_LEFT_BACKUP)) {
                    hintsLeft = savedInstanceState.getInt(HINTS_LEFT_BACKUP);
                    hintsLeftTextView.setText(hintsLeft+"");
                }
                if (savedInstanceState.containsKey(TRIALS_LEFT_BACKUP)) {
                    trialsLeft = savedInstanceState.getInt(TRIALS_LEFT_BACKUP);
                    trialsLeftTextView.setText(trialsLeft+"");
                }
                if (savedInstanceState.containsKey(UP_LIM_4_HINTS))
                    upperLimitForHint = savedInstanceState.getInt(UP_LIM_4_HINTS);
                if (savedInstanceState.containsKey(LOW_LIM_4_HINTS))
                    lowerLimitForHint = savedInstanceState.getInt(LOW_LIM_4_HINTS);
                if (savedInstanceState.containsKey(START_BOOL_BACKUP))
                    start = savedInstanceState.getBoolean(START_BOOL_BACKUP);
                if (savedInstanceState.containsKey(CLEAR_BOOL_BACKUP))
                    clear = savedInstanceState.getBoolean(CLEAR_BOOL_BACKUP);
                if (savedInstanceState.containsKey(INGAME_BOOL_BACKUP))
                    inGame = savedInstanceState.getBoolean(INGAME_BOOL_BACKUP);
                if (savedInstanceState.containsKey(TRIALS_LEFT_TV_COLOR_BACKUP)) {
                    trials_left_color = savedInstanceState.getInt(TRIALS_LEFT_TV_COLOR_BACKUP);
                    trialsLeftTextView.setBackgroundColor(trials_left_color);
                }
                if (savedInstanceState.containsKey(SCORE_BACKUP))
                    score = savedInstanceState.getInt(SCORE_BACKUP);
                if(savedInstanceState.containsKey(SCORE_TV_ON)){
                    score_Tv_On = savedInstanceState.getBoolean(SCORE_TV_ON);
                    if(score_Tv_On) {
                        scoreTextView.setVisibility(View.VISIBLE);
                        scoreTextView.setText(getString(R.string.score)+score);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void startGame(int level){
        start = true;
        inGame = true;
        lowerLimitForHint = 0;
        scoreTextView.setVisibility(View.VISIBLE);
        score_Tv_On = true;
        if(startButton.getText().toString().equals(getString(R.string.next_level))) {
            ++currentLevel;
            level = currentLevel;
            score+=MAX_SCORE_PER_LEVEL;
        }
        scoreTextView.setText(getString(R.string.score)+score);
        startButton.setText(getString(R.string.restart));
        trials_left_color = getResources().getColor(R.color.colorPrimaryDark);
        trialsLeftTextView.setBackgroundColor(trials_left_color);

        String disp;
        switch (level){
            case 1:
                disp = getString(R.string.level1);
                upperLimitForHint = LEVEL_ONE_MAX;
                hintsLeft = level;
                break;
            case 2:
                disp = getString(R.string.level2);
                upperLimitForHint = LEVEL_TWO_MAX;
                hintsLeft = level;
                break;
            case 3:
                disp = getString(R.string.level3);
                upperLimitForHint = LEVEL_THREE_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            case 4:
                disp = getString(R.string.level4);
                upperLimitForHint = LEVEL_FOUR_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            case 5:
                disp = getString(R.string.level5);
                upperLimitForHint = LEVEL_FIVE_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            case 6:
                disp = getString(R.string.level6);
                upperLimitForHint = LEVEL_SIX_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            case 7:
                disp = getString(R.string.level7);
                upperLimitForHint = LEVEL_SEVEN_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            case 8:
                disp = getString(R.string.level8);
                upperLimitForHint = LEVEL_EIGHT_MAX;
                hintsLeft = NUM_HINTS_FOR_LEVEL_3_AND_ABOVE;
                break;
            default:
                disp = null;
        }

        setDisplay(disp);

        numberToBeGuessed = generateNum(level);
        trialsLeft = getAverageComputerGuessCount(numberToBeGuessed,level,0,1)+EXTRA_GUESS;
        hintsLeftTextView.setText(hintsLeft+"");


        switch (level){
            case 1:levelTextView.setText(getString(R.string.level1Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 2:levelTextView.setText(getString(R.string.level2Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 3:levelTextView.setText(getString(R.string.level3Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 4:levelTextView.setText(getString(R.string.level4Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 5:levelTextView.setText(getString(R.string.level5Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 6:levelTextView.setText(getString(R.string.level6Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 7:levelTextView.setText(getString(R.string.level7Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
            case 8:levelTextView.setText(getString(R.string.level8Tag));
                trialsLeftTextView.setText(trialsLeft+"");
                break;
        }
    }

    public void numBtnClicked(View view){
        if(!start||!inGame)
            return;
        Button b = (Button)view;
        String buttonNumber = b.getText().toString();
        if(view.getId()==deleteB.getId()&&start)
            delete();
            else{
            if (start&&!clear) {
                updateDisplay(buttonNumber);
            }
            else {
                setDisplay(buttonNumber);
                clear = false;
            }
        }
    }

    private int getAverageComputerGuessCount(int correctAnswer,int level,int previousSum,int count){

        if(count>3)
            return previousSum/3;

        Random r = new Random();
        int lowerLimit = 0;
        int upperLimit;

        switch (level){
            case 1:
                upperLimit = LEVEL_ONE_MAX;
                break;
            case 2:
                upperLimit = LEVEL_TWO_MAX;
                break;
            case 3:
                upperLimit = LEVEL_THREE_MAX;
                break;
            case 4:
                upperLimit = LEVEL_FOUR_MAX;
                break;
            case 5:
                upperLimit = LEVEL_FIVE_MAX;
                break;
            case 6:
                upperLimit = LEVEL_SIX_MAX;
                break;
            case 7:
                upperLimit = LEVEL_SEVEN_MAX;
                break;
            case 8:
                upperLimit = LEVEL_EIGHT_MAX;
                break;
            default:
                upperLimit = -1;
        }
        int guessCount = 0;
        int computerGuess;
        do{
            computerGuess = lowerLimit + r.nextInt(upperLimit+1);
            if(computerGuess>correctAnswer){
                int maxGuess = lowerLimit+upperLimit;
                int amountToRemove = maxGuess-computerGuess;
                upperLimit-=(amountToRemove+1);

            }
            else if(computerGuess<correctAnswer){
                int diffBtwGuessAndLowerLimit = computerGuess - lowerLimit;
                lowerLimit+=(diffBtwGuessAndLowerLimit+1);
                upperLimit-=(diffBtwGuessAndLowerLimit+1);
            }
            guessCount++;
        }
        while(computerGuess!=correctAnswer);
        return getAverageComputerGuessCount(correctAnswer,level,previousSum+guessCount,++count);
    }

    private void setDisplay(String toBeDisplayed){
        displayTextView.setText(toBeDisplayed);
        clear = true;
    }

    private void updateDisplay(String toBeAppended){
        displayTextView.setText(displayTextView.getText().toString()+toBeAppended);
    }

    private void delete(){
        String currentText = displayTextView.getText().toString();
        if(currentText.trim().isEmpty())
            return;
        displayTextView.setText(currentText.substring(0,currentText.length()-1));
    }

    private void updateGame(boolean currentLevelWon){
        if(currentLevelWon)
            if(currentLevel==MAX_LEVEL) {
                setDisplay(getString(R.string.game_won));
                inGame = false;
            }
            else {
                start  = false;
                nextLevel();
            }
        else {
            setDisplay(getString(R.string.game_lost)+"\n"+getString(R.string.the_corr_ans_is)+" "+numberToBeGuessed);
            inGame = false;
        }

    }

    private void nextLevel(){

        startButton.setText(getString(R.string.next_level));
    }

    private void validateAnswer(int userGuess){
        int maxGuess,diffBtwGuessAndLowerLimit;
        if(userGuess<numberToBeGuessed) {
            diffBtwGuessAndLowerLimit = userGuess - lowerLimitForHint;
            lowerLimitForHint+=(diffBtwGuessAndLowerLimit+1);
            upperLimitForHint-=(diffBtwGuessAndLowerLimit+1);
            score-=SCORE_DROP_BY_TRIAL;
            if(score<=0)
                score = 0;
            setDisplay(userGuess + " " + getString(R.string.too_low));
        }
        else if(userGuess>numberToBeGuessed) {
            maxGuess = lowerLimitForHint + upperLimitForHint;
            int amountToRemove = maxGuess - userGuess;
            upperLimitForHint -= (amountToRemove + 1);
            score-=SCORE_DROP_BY_TRIAL;
            if(score<=0)
                score = 0;
            setDisplay(userGuess + " " + getString(R.string.too_high));
        }
        else if(userGuess==numberToBeGuessed) {
            setDisplay(userGuess + " "+getString(R.string.correct));
            updateGame(true);
        }
        trialsLeft--;
        trialsLeftTextView.setText(trialsLeft+"");
        if(trialsLeft==1) {
            trials_left_color = getResources().getColor(R.color.colorAccent);
            trialsLeftTextView.setBackgroundColor(trials_left_color);
        }
        if(trialsLeft<1&&userGuess!=numberToBeGuessed)
            updateGame(false);

        scoreTextView.setText(getString(R.string.score)+score);
    }


    private int generateNum(int level){
        Random r = new Random();
        switch (level){
            case 1:return r.nextInt(101);
            case 2:return r.nextInt(501);
            case 3:return r.nextInt(1001);
            case 4:return r.nextInt(5001);
            case 5:return r.nextInt(10001);
            case 6:return r.nextInt(50001);
            case 7:return r.nextInt(100001);
            case 8:return r.nextInt(500001);
        }
        return 0;
    }

    public void userHasSubmitted(View view){
        try {
            int userGuess = Integer.parseInt(displayTextView.getText().toString());
            validateAnswer(userGuess);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void giveHint(){
        int diffBtwGuessAndLowerLimit,maxGuess,amountToRemove;
        if(hintsLeft<1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.hint)).setMessage(getString(R.string.no_more_hints)).show();
            return;
        }
        Random r = new Random();
        String guessHint = getString(R.string.the_num_is)+" ";
        int randomGuessInRange;
        score-=SCORE_DROP_BY_HINT;
        if(score<=0)
            score = 0;

        do {
            randomGuessInRange = lowerLimitForHint + r.nextInt(upperLimitForHint+1);
            if(upperLimitForHint<1){
                upperLimitForHint+=1;
                lowerLimitForHint-=1;
            }
            if (randomGuessInRange > numberToBeGuessed) {
                    maxGuess = lowerLimitForHint+upperLimitForHint;
                    amountToRemove = maxGuess-randomGuessInRange;
                    upperLimitForHint-=(amountToRemove+1);
                guessHint += getString(R.string.less_than) + " " + randomGuessInRange;
            }
            else if (randomGuessInRange < numberToBeGuessed) {
                diffBtwGuessAndLowerLimit = randomGuessInRange - lowerLimitForHint;
                lowerLimitForHint += (diffBtwGuessAndLowerLimit + 1);
                upperLimitForHint -= (diffBtwGuessAndLowerLimit + 1);
                guessHint += getString(R.string.greater_than) + " " + randomGuessInRange;
            }
        }
        while (randomGuessInRange==numberToBeGuessed);

        AlertDialog.Builder hint = new AlertDialog.Builder(this);
        hint.setTitle(getString(R.string.hint)).setMessage(guessHint).show();
        hintsLeft--;
        hintsLeftTextView.setText(hintsLeft+"");
        scoreTextView.setText(getString(R.string.score)+score);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DISPLAY_BACKUP,displayTextView.getText().toString());
        outState.putInt(GUESS_NUM_BACKUP,numberToBeGuessed);
        outState.putInt(LEVEL_BACKUP,currentLevel);
        outState.putString(START_BTN_TEXT,startButton.getText().toString());
        outState.putInt(HINTS_LEFT_BACKUP,hintsLeft);
        outState.putInt(TRIALS_LEFT_BACKUP,trialsLeft);
        outState.putInt(UP_LIM_4_HINTS,upperLimitForHint);
        outState.putInt(LOW_LIM_4_HINTS,lowerLimitForHint);
        outState.putBoolean(START_BOOL_BACKUP,start);
        outState.putBoolean(CLEAR_BOOL_BACKUP,clear);
        outState.putBoolean(INGAME_BOOL_BACKUP,inGame);
        outState.putInt(TRIALS_LEFT_TV_COLOR_BACKUP,trials_left_color);
        outState.putInt(SCORE_BACKUP,score);
        outState.putBoolean(SCORE_TV_ON,score_Tv_On);
    }
}
