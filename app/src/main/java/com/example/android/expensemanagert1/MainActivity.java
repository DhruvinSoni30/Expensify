package com.example.android.expensemanagert1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button seeexpense,addexpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seeexpense = (Button)findViewById(R.id.view);
        addexpense = (Button)findViewById(R.id.add);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        seeexpense.startAnimation(myanim);
        addexpense.startAnimation(myanim);

        seeexpense.setOnClickListener(this);
        addexpense.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add:
                Intent i = new Intent(this, Main3Activity.class);
                startActivity(i);
                break;
            case R.id.view:
                Intent j = new Intent(this, DisplayActivity.class);
                startActivity(j);
                break;
        }

    }


}
