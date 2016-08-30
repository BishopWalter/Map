package com.zheng.map_c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    private AMap aMap = null;
    private Button btn_standard;
    private Button btn_satelite;
    private Button btn_night;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    //获取定位信息部分的回调接口
    //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
    //此为通过创建回调接口的方法实现
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            parseAMapLocation(aMapLocation);
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
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //定位功能
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式
        //高精度定位,GPS与网路定位结合，返回一个优先的定位数据，高耗电，当通过GPS定位时不会返回地址描述信息
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，且不会返回地址描述信息
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);

        //设置定位次数

        //单次定位
        //获取一次定位结果：
        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，
        // 启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);


        //自定义连续定位
        //SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔：
        mLocationOption.setInterval(2000);


        //其他参数
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为强制刷新。每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟软件Mock位置结果，多为模拟GPS定位结果，默认为false，不允许模拟位置。
        mLocationOption.setMockEnable(false);


        //定位
        //给定位客户端设置参数，mLocationOption包含很多定位信息的参数，并且都是通过返回值方式发出
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位客户端
        mLocationClient.startLocation();


        //获取定位结果：回调接口，解析AMapLocation对象方法

    }

    //解析AMapLocation对象具体方法
    public void parseAMapLocation(AMapLocation aMapLocation) {

        String add;
        double mLongitude,mLatitude;


        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                mLatitude = Math.round(aMapLocation.getLatitude());//获取纬度
                mLongitude = Math.round(aMapLocation.getLongitude());//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息

                add = aMapLocation.getCountry()+aMapLocation.getProvince()+aMapLocation.getCity();

                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                Toast.makeText(MainActivity.this,"经度:"+mLongitude+"\n纬度:"+mLatitude+"\n当前位置:"+add, Toast.LENGTH_SHORT).show();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }


    //地图模式切换
    public void findId() {
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
        //停止定位
//        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁


        //销毁定位客户端
        //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

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
