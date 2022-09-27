package com.halla.c201734025.roulette01;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ImageView iv_roulette;
    TextView text;
    float startDgree = 0f;
    float endDgree = 0f;
    boolean isRunning = false;
    boolean isCancel = false;
    ObjectAnimator obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_roulette = findViewById(R.id.roulette);
        text = findViewById(R.id.text);
        text.setVisibility(View.INVISIBLE);
        setTitle("오늘점심 뭐 먹지? 룰렛을 터치 하세요");

        iv_roulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning == false){
                    text.setVisibility(View.INVISIBLE);
                    isRunning = true;
                    startDgree = endDgree % 360;
                    Random rnd = new Random();
                    int degree_rnd = rnd.nextInt(360);
                    endDgree = startDgree + (360*4) + degree_rnd;
                    obj = ObjectAnimator.ofFloat(iv_roulette, "rotation", startDgree, endDgree);
                    obj.setInterpolator(new AccelerateDecelerateInterpolator());
                    obj.setDuration(3000);
                    obj.addListener(new Animator.AnimatorListener(){
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isRunning = false;
                            if(isCancel==false) display_result(endDgree% 360, 0);
                            isCancel = false;
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isRunning = false;
                            isCancel = true;
                            endDgree = (float)obj.getAnimatedValue();
                            display_result(endDgree% 360, 1);
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    obj.start();
                }else{
                    obj.cancel();
                }

            }
        });
    }
    public void display_result(float ed, int a){
        String sel, str;
        if(ed < 90){
            sel = "김치찌개";
        }else if(ed < 180){
            sel = "햄버거";
        }else if(ed < 270){
            sel = "돈까스";
        }else{
            sel = "짬뽕";
        }
        if(a != 1){
            str = "오늘 점심은 " + sel + " 입니다.";
        }else{
            str = "" + sel + " 먹고 싶구나.. 말하지 그랬어!!";
        }
        text.setText(str);
        text.setVisibility(View.VISIBLE);
    }
}