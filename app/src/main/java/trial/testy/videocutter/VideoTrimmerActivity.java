package trial.testy.videocutter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VideoTrimmerActivity extends AppCompatActivity {

    protected RangeSeekBar seekBar;
    protected RangeSeekBar seekbar5;
    protected LinearLayout activityMain;
    int duration = 50;
    float lastRightValue;
    float lastLeftValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_video_trimmer);
        initView();

        seekBar.setRange(0, duration, 1);
        seekBar.setSeekBarMode(RangeSeekBar.SEEKBAR_MODE_RANGE);
        seekBar.setIndicatorText("something");
        seekBar.setTickMarkMode(RangeSeekBar.TRICK_MARK_MODE_NUMBER);

        ArrayList<String> durArray = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            durArray.add(String.valueOf(i));
        }

        final CharSequence[] charSequenceItems = durArray.toArray(new CharSequence[durArray.size()]);
        //seekbar5.setTickMarkTextArray(charSequenceItems);
        seekbar5.setIndicatorTextDecimalFormat("0");
        seekbar5.setValue(0,30);
        seekbar5.setRange(0,duration);

        seekbar5.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                //leftValue is left seekbar value, rightValue is right seekbar value
                float dx = rightValue - leftValue;

                if (dx>=15) {
                    if(lastRightValue<rightValue){
                       //right marker moved to right after reaching limit
                        seekbar5.setValue(leftValue+1,rightValue);
                        lastRightValue = rightValue;
                        lastLeftValue = leftValue;
                    }else{
                        //left was moved left after limit
                        seekbar5.setValue(leftValue,rightValue-1);
                        lastRightValue = rightValue-1;
                        lastLeftValue = leftValue;
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //start tracking touch
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //stop tracking touch
            }
        });

    }

    private void initView() {
        seekBar = (RangeSeekBar) findViewById(R.id.seekBar);
        seekbar5 = (RangeSeekBar) findViewById(R.id.seekbar5);
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
    }
}
