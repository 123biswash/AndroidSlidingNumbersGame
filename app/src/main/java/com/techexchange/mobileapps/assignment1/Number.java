package com.techexchange.mobileapps.assignment1;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

class Number {
    protected SparseArray<ArrayList<Bitmap>> imageDictionary;
    protected ImageView[] imageViewArray = new ImageView[9];
    protected GridLayout mainGrid;
    protected int[] arrChange;
    protected int[] solveStateArr;
    protected Bitmap[] solveState = new Bitmap[9];

    public boolean isLegalMove(ImageView img){
        boolean isTrue = false;
        int[] indexArr = {+1, -1, +3, -3};
        int indexEmpty = findEmpty();
        int indexNum = getIndex(img);

        for (int j : indexArr){
            int sum = indexNum + j;
            if ((sum < 0) || (sum > 8)) {
                isTrue = false;
            }
            if(sum == indexEmpty){
                if((indexNum == 2 || indexNum == 5)&& (j == 1)){
                     isTrue = false;
                } else if((indexNum == 3 || indexNum == 6)&& j == -1) {
                    isTrue = false;
                } else {
                    return true;
                }
            }
        }
        return isTrue;
    }

    protected int getIndex(ImageView img){
        int indexNum=-1;
        for(int i = 0; i < mainGrid.getChildCount();i++){
            if(img == (ImageView) mainGrid.getChildAt(i)){
                indexNum = i;
            }
        }
        return indexNum;
    }

    public int[] getArrChange() {
        return arrChange;
    }

    public int[] getSolveStateArr() {
        return solveStateArr;
    }

    protected int findEmpty(){
        for(int i=0;i<9;i++){
            if(imageViewArray[i].getDrawable() == null){
                return i;
            }
        }
        return 0;
    }
    protected boolean ifPuzzleSolved() {
        //return model.solveState.equals(getState());
        if (Arrays.equals(getArrChange(), getSolveStateArr())){
            return true;
        }
        return false;
    }
    protected SparseArray<ArrayList<Bitmap>> makeBitmap(Bitmap mainImage){
        int height = mainImage.getHeight();
        int width = mainImage.getWidth();
        SparseArray<ArrayList<Bitmap>> listOfBitMapImages = new SparseArray<ArrayList<Bitmap>>();
        for(int i=0;i<10;i++){
            Bitmap temp = Bitmap.createBitmap(mainImage, (width/10)*i, 0, width/10, height);
            ArrayList<Bitmap> tempStore = new ArrayList<Bitmap>();
            for(int j=0; j<3; j++){
                tempStore.add(Bitmap.createBitmap(temp, 0, (height/3)*j, width/10, height/3));
            }
            listOfBitMapImages.append(i, tempStore);
        }
        return listOfBitMapImages;
    }


}
