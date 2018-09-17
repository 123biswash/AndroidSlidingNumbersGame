package com.techexchange.mobileapps.assignment1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =  MainActivity.class.getSimpleName();
    private ImageView img;
    private ImageView solveStateImg;
    private Number model = new Number();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the bitmap into mapping.
        Bitmap images = BitmapFactory.decodeResource(getResources(),
                R.drawable.sprite);
        model.imageDictionary = model.makeBitmap(images);
        //Replace 0's image with null
        model.imageDictionary.get(0).set(0, null);
        model.imageDictionary.get(0).set(1, null);
        model.imageDictionary.get(0).set(2, null);
        // Get the main grid.
        model.mainGrid = findViewById(R.id.GridLayout1);
        for(int i = 0; i < model.mainGrid.getChildCount();i++)
        {
            model.imageViewArray[i]= (ImageView) model.mainGrid.getChildAt(i);
        }
        for(int i = 0; i < model.solveState.length;i++)
        {
            model.solveState[i]= model.imageDictionary.get(i).get(0);
        }
        model.solveState[8] = null;
        model.arrChange = new int[]{0,1,2,3,4,5,6,7,8};
        model.solveStateArr = new int[]{1,2,3,4,5,6,7,8,0};
        // Get image view for each bitmap and setOnCLickListener to each imageview
        setGridImagesAndColor(0);
        shuffle();
    }

    private Bitmap[] getState() {
        Bitmap[] state = new Bitmap[9];

        for (int i = 0; i < model.mainGrid.getChildCount(); ++i) {
            ImageView imageView = (ImageView) model.mainGrid.getChildAt(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                state[i] = null;
            } else {
                Bitmap bitmap = ((BitmapDrawable) (drawable)).getBitmap();
                state[i] = bitmap;
            }
        }
        return state;
    }

    private void setGridImagesAndColor(int color){
        if(color==0){
            for (int i = 0; i < model.mainGrid.getChildCount(); i++) {
                img = (ImageView) model.mainGrid.getChildAt(i);
                if (i == model.findEmpty()) {
                    img.setImageDrawable(null);
                } else {
                    img.setImageBitmap(model.imageDictionary.get(i).get(color));
                }
                img.setOnClickListener(this::onImagePressed);

            }
        }else if(color==2){
            for (int i = 1; i < model.mainGrid.getChildCount(); i++) {
                img = (ImageView) model.mainGrid.getChildAt(i - 1);
                img.setImageBitmap(model.imageDictionary.get(i).get(color));
            }

        }else{
            return;
        }

    }

    private void onImagePressed(View v) {
        if(model.isLegalMove((ImageView) v)) {
            int empty= model.findEmpty();
            int pressedIndex = model.getIndex((ImageView) v);
            ImageView pressedView = (ImageView) v;
            ImageView emptyView = model.imageViewArray[empty];
            emptyView.setImageDrawable(pressedView.getDrawable());
            pressedView.setImageDrawable(null);

            model.arrChange[empty] = model.arrChange[pressedIndex];
            model.arrChange[pressedIndex] = 0;

            if(model.ifPuzzleSolved()){
                setGridImagesAndColor(2);
                Toast.makeText(MainActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void shuffle(){
        Random r = new Random();
        for (int i =0; i<r.nextInt(20)+10;i++){
            ImageView x = model.imageViewArray[r.nextInt(model.mainGrid.getChildCount())];
            while(model.isLegalMove(x)) {
                int a = model.findEmpty();
                int b = model.getIndex(x);
                ImageView emptyView = model.imageViewArray[model.findEmpty()];
                emptyView.setImageDrawable(x.getDrawable());
                x.setImageDrawable(null);
                model.arrChange[a] = model.arrChange[b];
                model.arrChange[b] = 0;
            }

        }
    }

    // Add the UpdateUI here.
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() was called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() was called");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() was called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() was called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() was called");
    }


}