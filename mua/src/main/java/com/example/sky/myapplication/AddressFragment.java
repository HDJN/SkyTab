package com.example.sky.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doouya.mua.view.ScreenShotable;

public class AddressFragment extends Fragment implements ScreenShotable {

    private Bitmap bitmap;
    private View containerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab03, container, false);
        containerView = rootView.findViewById(R.id.container);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                AddressFragment.this.bitmap = bitmap;
            }
        }.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
