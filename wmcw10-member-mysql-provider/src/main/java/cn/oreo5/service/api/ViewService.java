package cn.oreo5.service.api;


public interface ViewService {

    /**
     * 记录主页访问量
     */
    void updateIndexView();

    /**
     * 记录酒店访问量
     */
    void updateHotelView();

    /**
     * 记录美食访问量
     */
    void updateCateView();

    /**
     * 记录地图访问量
     */
    void updateMapView();
}
