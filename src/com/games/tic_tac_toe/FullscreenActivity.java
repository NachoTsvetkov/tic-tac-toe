package com.games.tic_tac_toe;

import com.games.tic_tac_toe.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                        } else {
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            //delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    //mSystemUiHider.toggle();
                } else {
                    //mSystemUiHider.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        // delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    public static String strBtnValue = "X";
    
    public void onBoardBtnClick(View view) {
    	Button btn = (Button)view;
    	if (btn.getText().equals("")) {

        	btn.setText(strBtnValue);
        	checkForWin();
        	
        	strBtnValue = strBtnValue == "X" ? "O" : "X";
		}
    }

	private void checkForWin() {
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		Button b3 = (Button)findViewById(R.id.button3);
		Button b4 = (Button)findViewById(R.id.button4);
		Button b5 = (Button)findViewById(R.id.button5);
		Button b6 = (Button)findViewById(R.id.button6);
		Button b7 = (Button)findViewById(R.id.button7);
		Button b8 = (Button)findViewById(R.id.button8);
		Button b9 = (Button)findViewById(R.id.button9);
		
		boolean firstLineWin = compare3Strings(b1.getText(),b2.getText(), b3.getText());
		boolean secondLineWin = compare3Strings(b4.getText(),b5.getText(), b6.getText());
		boolean thirdLineWin = compare3Strings(b7.getText(),b8.getText(), b9.getText());
		boolean fourthLineWin = compare3Strings(b1.getText(),b4.getText(), b7.getText());
		boolean fifthLineWin = compare3Strings(b2.getText(),b5.getText(), b8.getText());
		boolean sixthLineWin = compare3Strings(b3.getText(),b6.getText(), b9.getText());
		boolean seventhLineWin = compare3Strings(b1.getText(),b5.getText(), b9.getText());
		boolean eightLineWin = compare3Strings(b3.getText(),b5.getText(), b7.getText());
		
		boolean win = firstLineWin ||
				secondLineWin ||
				thirdLineWin ||
				fourthLineWin ||
				fifthLineWin ||
				sixthLineWin ||
				seventhLineWin ||
				eightLineWin;
		
		if (win) {
			TextView text = (TextView)findViewById(R.id.textView1);
			text.setText("WIN");
			
			b1.setEnabled(false);
			b2.setEnabled(false);
			b3.setEnabled(false);
			b4.setEnabled(false);
			b5.setEnabled(false);
			b6.setEnabled(false);
			b7.setEnabled(false);
			b8.setEnabled(false);
			b9.setEnabled(false);
		}
	}
	
	private boolean compare3Strings(CharSequence str1, CharSequence str2, CharSequence str3) {
		return !str1.equals("") && str1.equals(str2) && str1.equals(str3);
	}
	
	public void onRestartBtnClick(View view) {
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		Button b3 = (Button)findViewById(R.id.button3);
		Button b4 = (Button)findViewById(R.id.button4);
		Button b5 = (Button)findViewById(R.id.button5);
		Button b6 = (Button)findViewById(R.id.button6);
		Button b7 = (Button)findViewById(R.id.button7);
		Button b8 = (Button)findViewById(R.id.button8);
		Button b9 = (Button)findViewById(R.id.button9);
		
		TextView text = (TextView)findViewById(R.id.textView1);
		text.setText(R.string.strEmpty);
		
		b1.setText(R.string.strEmpty);
		b2.setText(R.string.strEmpty);
		b3.setText(R.string.strEmpty);
		b4.setText(R.string.strEmpty);
		b5.setText(R.string.strEmpty);
		b6.setText(R.string.strEmpty);
		b7.setText(R.string.strEmpty);
		b8.setText(R.string.strEmpty);
		b9.setText(R.string.strEmpty);

		b1.setEnabled(true);
		b2.setEnabled(true);
		b3.setEnabled(true);
		b4.setEnabled(true);
		b5.setEnabled(true);
		b6.setEnabled(true);
		b7.setEnabled(true);
		b8.setEnabled(true);
		b9.setEnabled(true);
		
		strBtnValue = "X";
	}
}
