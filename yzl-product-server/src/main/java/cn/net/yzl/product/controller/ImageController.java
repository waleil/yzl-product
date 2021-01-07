package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.model.db.ImageStore;
import cn.net.yzl.product.service.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("v1/insert")
    @ApiOperation("增加图片")
    public ComResponse insertImage(@RequestBody Image image){
        Integer id = imageService.insertImage(image);
        return ComResponse.success(id);
    }

    @PostMapping("v1/createAlbum")
    public ComResponse createAlbum(@RequestBody ImageStore imageStore){
        return imageService.createAlbum(imageStore);
    }

    @GetMapping("v1/selectByStoreId")
    public ComResponse  selectByStoreId(@RequestParam Integer id){
        return imageService.selectByStoreId(id);
    }

}