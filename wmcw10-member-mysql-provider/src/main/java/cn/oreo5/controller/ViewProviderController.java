package cn.oreo5.controller;

import cn.oreo5.service.api.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewProviderController {

    @Autowired
    private ViewService viewService;

    @RequestMapping("/user/visit/index/remote")
    void updateIndexViewRemote() {
        try {
            viewService.updateIndexView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/visit/hotel/remote")
    void updateHotelViewRemote() {
        try {
            viewService.updateHotelView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/visit/cate/remote")
    void updateCateViewRemote() {
        try {
            viewService.updateCateView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user/visit/map/remote")
    void updateMapViewRemote() {
        try {
            viewService.updateMapView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
