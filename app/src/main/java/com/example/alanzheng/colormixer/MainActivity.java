package com.example.alanzheng.colormixer;

import android.content.ClipData;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnTouchListener, View.OnDragListener{

    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private TextView redValueTextView;
    private TextView greenValueTextView;
    private TextView blueValueTextView;
    private LinearLayout mixerLinearLayout;
    private int redValue, greenValue, blueValue;
    private int newRedValue, newGreenValue, newBlueValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redSeekBar = (SeekBar) findViewById(R.id.redValueSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenValueSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueValueSeekBar);

        SeekBar[] seekBars = { redSeekBar, greenSeekBar, blueSeekBar };
        for (SeekBar seekBar : seekBars) {
            seekBar.setMax(255);
            seekBar.setOnSeekBarChangeListener(this);
        }

        redValueTextView = (TextView) findViewById(R.id.redValueTextView);
        redValueTextView.setText("R = " + 0);
        greenValueTextView = (TextView) findViewById(R.id.greenValueTextView);
        greenValueTextView.setText("R = " + 0);
        blueValueTextView = (TextView) findViewById(R.id.blueValueTextView);
        blueValueTextView.setText("R = " + 0);

        TextView[] textViews = { redValueTextView, greenValueTextView, blueValueTextView };
        for (TextView textView : textViews) {
            textView.setOnTouchListener(this);
        }

        mixerLinearLayout = (LinearLayout) findViewById(R.id.mixerLinearLayout);
        mixerLinearLayout.setOnDragListener(this);

        newRedValue = newGreenValue = newBlueValue = 0;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.redValueSeekBar) {
            redValue = progress;
            redValueTextView.setText("R = " + redValue);
        } else if (seekBar.getId() == R.id.greenValueSeekBar) {
            greenValue = progress;
            greenValueTextView.setText("G = " + greenValue);
        } else if (seekBar.getId() == R.id.blueValueSeekBar) {
            blueValue = progress;
            blueValueTextView.setText("B = " + blueValue);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(clipData, shadowBuilder, v, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                Log.d(this.getClass().getSimpleName(), "Dragged to here");
                TextView view = (TextView) event.getLocalState();
                if (view.getId() == R.id.redValueTextView) {
                    newRedValue = redValue;
                } else if (view.getId() == R.id.greenValueTextView) {
                    newGreenValue = greenValue;
                } else if (view.getId() == R.id.blueValueTextView) {
                    newBlueValue = blueValue;
                }

                v.setBackgroundColor(Color.rgb(newRedValue, newGreenValue, newBlueValue));
                break;
            default:
                break;
        }
        return true;
    }
}
