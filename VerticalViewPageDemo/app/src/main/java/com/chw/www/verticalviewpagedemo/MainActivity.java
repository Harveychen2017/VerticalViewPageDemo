package com.chw.www.verticalviewpagedemo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager mViewPage;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();

    }
    int lastSelectIndex = -1 ;
    private void initListener() {
        //翻页的时候把上一个页面中展示的视频控件显示到当前的页面上
        mViewPage.setPageTransformer(false, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View page, float position) {
                //当前选中的页面
                int currentItem = mViewPage.getCurrentItem();
                //避免有人上下滚动不翻页(用记录的方法)
                if (position==0&&currentItem!=lastSelectIndex){    //该view参数正好完全、显示出来
                    lastSelectIndex=currentItem;

                    ViewParent parent = mTextView.getParent();
                    if (parent!=null) {
                        //添加之前要从父控件中移除，才能再次添加
                        ((ViewGroup)parent).removeView(mTextView);
                    }

                    ((ViewGroup)page).addView(mTextView);
                }
            }
        });
    }

    private void initData() {


        ArrayList<RelativeLayout> relativeLayouts = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
            relativeLayout.setBackgroundResource(R.drawable.room_change_bg);
            relativeLayouts.add(relativeLayout);
        }
        MyAdapter myAdapter = new MyAdapter(relativeLayouts);
        mViewPage.setAdapter(myAdapter);
    }

    private void initView() {
        mViewPage = (VerticalViewPager) findViewById(R.id.viewPage);

        mTextView = new TextView(getApplicationContext());
        mTextView.setText("一个假视频控件");
        mTextView.setTextSize(60);
    }

    private class MyAdapter extends PagerAdapter{
        ArrayList<RelativeLayout> mViewGroups;
        public MyAdapter(ArrayList<RelativeLayout> relativeLayouts) {
            this.mViewGroups=relativeLayouts;
        }

        @Override
        public int getCount() {
            return mViewGroups==null?0:mViewGroups.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout relativeLayout = mViewGroups.get(position);
            container.addView(relativeLayout);
            return relativeLayout;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
