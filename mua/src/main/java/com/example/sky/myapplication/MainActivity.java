package com.example.sky.myapplication;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.doouya.mua.view.DrawableButton;
import com.doouya.mua.view.OnDouClickListener;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


public class MainActivity extends FragmentActivity {

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    private NavBarController mController;

    private FrameLayout mContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mController = new NavBarController((ViewGroup) findViewById(R.id.layout_main_nav), MainActivity.this);
        mContentView = (FrameLayout)findViewById(R.id.container);
        setSelect(0);

    }


    protected void startRevealTransition(int offset){
        final Rect bounds = new Rect();
        mContentView.getHitRect(bounds);
        int centerX = (bounds.right / 5) * offset + (bounds.right/10);
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mContentView,
                centerX, bounds.bottom, 0, hypo(bounds.height(), bounds.width()));
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static float hypo(float a, float b){
        return (float) Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new WeixinFragment();
                    transaction.add(R.id.container, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new FrdFragment();
                    transaction.add(R.id.container, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new AddressFragment();
                    transaction.add(R.id.container, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new SettingFragment();
                    transaction.add(R.id.container, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }







    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }


    class NavBarController implements View.OnClickListener, OnDouClickListener {
        private Boolean hasMessage = Boolean.valueOf(false);
        private MainActivity mActivity;
        private DrawableButton mBtnBaby;
        private DrawableButton mBtnChannel;
        private DrawableButton mBtnDiscover;
        private DrawableButton mBtnNotice;
        private ImageView mBtnShot;
        private View mCurrentHightLight;
        private ViewGroup mLayout;
        private View mNewNoticeMark;

        NavBarController(ViewGroup viewGroup, MainActivity mainActivity) {
            this.mLayout = viewGroup;
            this.mBtnChannel = ((DrawableButton) this.mLayout.findViewById(R.id.btn_nav_channel));
            this.mBtnDiscover = ((DrawableButton) this.mLayout.findViewById(R.id.btn_nav_discover));
            this.mBtnShot = ((ImageView) this.mLayout.findViewById(R.id.btn_nav_shot));
            this.mBtnNotice = ((DrawableButton) this.mLayout.findViewById(R.id.btn_nav_notice));
            this.mBtnBaby = ((DrawableButton) this.mLayout.findViewById(R.id.btn_nav_baby));
            this.mNewNoticeMark = this.mLayout.findViewById(R.id.view_notice_indicate);
            this.mCurrentHightLight = this.mBtnChannel;
            this.mBtnChannel.setOnClickListener(this);
            this.mBtnDiscover.setOnClickListener(this);
            this.mBtnShot.setOnClickListener(this);
            this.mBtnNotice.setOnClickListener(this);
            this.mBtnBaby.setOnClickListener(this);
            this.mBtnChannel.setOnDouClickListener(this);
            this.mBtnDiscover.setOnDouClickListener(this);
            this.mBtnBaby.setOnDouClickListener(this);
            this.mNewNoticeMark.setVisibility(View.INVISIBLE);
            this.mActivity = mainActivity;
            light(this.mCurrentHightLight);
        }

        private void dark(View view) {
            view.setSelected(false);
        }

        private void light(View view) {
            view.setSelected(true);
        }

        public void OnDouClick(View view) {
            if (this.mCurrentHightLight != view) {
                return;
            }
            Toast.makeText(MainActivity.this, "双击", Toast.LENGTH_SHORT).show();
        }

        public void hasMessage(Boolean bool) {
            if ((this.hasMessage == bool) || (this.mCurrentHightLight == this.mBtnNotice)) {
                return;
            }
            this.hasMessage = bool;
            if (this.hasMessage.booleanValue()) {
                this.mNewNoticeMark.setVisibility(View.VISIBLE);
                return;
            }
            this.mNewNoticeMark.setVisibility(View.INVISIBLE);
        }

        public void onClick(View view) {
            if ((this.mCurrentHightLight == null) || (this.mCurrentHightLight == view)) {
                return;
            }
            if (view == this.mBtnNotice) {
                hasMessage(Boolean.valueOf(false));
            }
            if (view != this.mBtnShot) {
                dark(this.mCurrentHightLight);
                light(view);
                this.mCurrentHightLight = view;
            }
            //执行跳转
            switch (view.getId()) {
                case R.id.btn_nav_channel:
                    startRevealTransition(0);
                    setSelect(0);
                    return;
                case R.id.btn_nav_discover:
                    startRevealTransition(1);
                    setSelect(1);
                    return;
                case R.id.btn_nav_notice:
                    startRevealTransition(3);
                    setSelect(2);
                    return;
                case R.id.btn_nav_baby:
                    startRevealTransition(4);
                    setSelect(3);
                    return;
                default:
                    return;
            }
        }

        public void swtich(int id) {
            switch (id) {
                case R.id.btn_nav_channel:
                    this.mBtnChannel.performClick();
                    return;
                case R.id.btn_nav_discover:
                    this.mBtnDiscover.performClick();
                    return;
                case R.id.btn_nav_notice:
                    this.mBtnNotice.performClick();
                    return;
                case R.id.btn_nav_baby:
                    this.mBtnBaby.performClick();
                    return;
                default:
                    return;
            }
        }
    }


}
