package com.alexlesaka.carshare.myviews;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alexlesaka.carshare.R;

/**
 * Created by aabuin on 24/08/2017.
 */

public class LoadingEditText extends LinearLayout
{
    private EditText editText;
    private ProgressBar progressBar;
    private Button checkButton;
    private Button createButton;

    public LoadingEditText(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.view_loading_textview, this,true);
        editText = (EditText) findViewById(R.id.loading_textview_value);
        progressBar = (ProgressBar) findViewById(R.id.loading_textview_progressbar);
        createButton = (Button) findViewById(R.id.loading_createusername_button);
        checkButton = (Button) findViewById(R.id.loading_check_button);
        setOff();
    }

    public void setOff()
    {
        progressBar.setVisibility(GONE);
        createButton.setVisibility(GONE);
    }

    public void setThinking()
    {
        createButton.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    public void setYes()
    {
        progressBar.setVisibility(GONE);
        createButton.setVisibility(VISIBLE);

    }
    public void setNo()
    {
        progressBar.setVisibility(GONE);
        createButton.setVisibility(GONE);
    }

    public EditText getEditText(){return editText;}

    public void setCheckButtonOnClick(View.OnClickListener listener)
    {
        checkButton.setOnClickListener(listener);
    }

    public void setCreateButtonOnClick(View.OnClickListener listener)
    {
        createButton.setOnClickListener(listener);
    }

}
