package com.idisfkj.zoomable;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.idisfkj.zoomable.common.ZoomableActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FIRST_IMAGE = 0;
    private final int SECOND_IMAGE = 1;
    private final int THIRD_IMAGE = 2;
    private SimpleDraweeView mFirstImage, mSecondImage, mThirdImage;
    private ArrayList<String> mPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setData();
        setImages();
    }

    private void initView() {
        mFirstImage = (SimpleDraweeView) findViewById(R.id.first_image);
        mSecondImage = (SimpleDraweeView) findViewById(R.id.second_image);
        mThirdImage = (SimpleDraweeView) findViewById(R.id.third_image);
    }

    private void setListener() {
        mFirstImage.setOnClickListener(this);
        mSecondImage.setOnClickListener(this);
        mThirdImage.setOnClickListener(this);
    }

    private void setData() {
        mPaths = new ArrayList<>();
        mPaths.add("http://img01.sogoucdn.com/app/a/100520024/b0badde998aa4fa9fd13212c13332c61");
        mPaths.add("http://img02.sogoucdn.com/app/a/100520024/332b27eeb743580f8028eab1f1dda149");
        mPaths.add("http://img01.sogoucdn.com/app/a/100520024/f2e39c989e6123722f9ce98eda22fd32");
    }

    private void setImages() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x / 3;
        mFirstImage.getLayoutParams().width = width;
        mFirstImage.setAspectRatio(1.0f);
        mSecondImage.getLayoutParams().width = width;
        mSecondImage.setAspectRatio(1.0f);
        mThirdImage.getLayoutParams().width = width;
        mThirdImage.setAspectRatio(1.0f);
        displayImage(mFirstImage, mPaths.get(FIRST_IMAGE));
        displayImage(mSecondImage, mPaths.get(SECOND_IMAGE));
        displayImage(mThirdImage, mPaths.get(THIRD_IMAGE));
    }

    private void displayImage(SimpleDraweeView draweeView, String url) {
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        //设置占位图
        if (hierarchy == null) {
            hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                    .setPlaceholderImage(getResources().getDrawable(R.mipmap.ic_launcher)
                            , ScalingUtils.ScaleType.CENTER).build();
            draweeView.setHierarchy(hierarchy);
        } else {
            hierarchy.setPlaceholderImage(getResources().getDrawable(R.mipmap.ic_launcher)
                    , ScalingUtils.ScaleType.CENTER);
        }

        //构建ImageRequestBuilder，传入请求Uri
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url));
        //构建Controller
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(requestBuilder.build())
                .build();
        //设置图片，Fresco设置显示图片都是通过Controller来实现
        draweeView.setController(controller);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_image:
                ZoomableActivity.goToPage(this, mPaths, FIRST_IMAGE);
                break;
            case R.id.second_image:
                ZoomableActivity.goToPage(this, mPaths, SECOND_IMAGE);
                break;
            case R.id.third_image:
                ZoomableActivity.goToPage(this, mPaths, THIRD_IMAGE);
                break;
        }
    }
}
