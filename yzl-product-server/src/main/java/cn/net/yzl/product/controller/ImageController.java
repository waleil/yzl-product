package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("v1/insert")
    public ComResponse insertImage(@RequestParam("url")String url, @RequestParam("type") Integer type){
        Integer id = imageService.insertImage(url,type);
        return ComResponse.success(id);
    }

}