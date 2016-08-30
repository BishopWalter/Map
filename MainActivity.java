package com.zheng.map_c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    private AMap aMap = null;
    private Button btn_standard;
    private Button btn_satelite;
    private Button btn_night;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Toast.makeText(MainActivity.this, "什么鬼啊", Toast.LENGTH_SHORT).show();
        }
    };

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //初始化定位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找出控件代表
        findId();
        //由于地图view没有生命周期，所以需要依赖Activity的生命周期，
        // 当Activity的onCreate执行时候，地图的mMapView.onCreate（）也执行
        mMapView.onCreate(savedInstanceState);
        //初始化一个地图
        if (aMap == null){
            aMap = mMapView.getMap();
        }
        //定位功能
        mLocationClient = new AMapLocationClient(getApplicationContext());
    //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式
        //高精度定位,GPS与网路定位结合，返回一个优先的定位数据
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //
    }




    //地图模式切换
    public void findId(){
        mMapView = (MapView) findViewById(R.id.map);
        btn_standard = (Button) findViewById(R.id.btn_standardMap);
        btn_satelite = (Button) findViewById(R.id.btn_satelliteMap);
        btn_night = (Button) findViewById(R.id.btn_nightMode);
    }
    public void btn_standardMap(View view) {
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
    }

    public void btn_satelliteMap(View view) {
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
    }

    public void btn_nightMode(View view) {
        aMap.setMapType(AMap.MAP_TYPE_NIGHT);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


}
