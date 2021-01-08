package cn.net.yzl.product.controller;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.model.db.ImageStore;
import cn.net.yzl.product.model.vo.image.ImageDTO;
import cn.net.yzl.product.model.vo.image.ImageVO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreDTO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreVO;
import cn.net.yzl.product.service.ImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("v1/insert")
    @ApiOperation("增加图片")
    public ComResponse insertImage(@RequestBody ImageVO image){
        Integer id = imageService.insertImage(image);
        return ComResponse.success(id);
    }

    @ApiOperation("创建图片库")
    @PostMapping("v1/createAlbum")
    public ComResponse createAlbum(@RequestBody ImageStoreVO imageStore){
        return imageService.createAlbum(imageStore);
    }

    @ApiOperation("查询图片库下所有图片")
    @GetMapping("v1/selectByStoreId")
    public ComResponse<Page<ImageDTO>>  selectByStoreId(@RequestParam Integer id, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize){
        return imageService.selectByStoreId(id,pageNo, pageSize);
    }

    @ApiOperation("查询图片库类型(0图片库1视频库)")
    @GetMapping("v1/selectTypeById")
    public ComResponse selectTypeById(@RequestParam("id") Integer id ){
        return imageService.selectTypeById(id);
    }

    @ApiOperation("删除图片")
    @GetMapping("v1/deleteById")
    public ComResponse deleteById(@RequestParam("id") Integer id,String userId){
        return imageService.deleteById(id,userId);
    }

    @ApiOperation("查询全部库（0图片库1视频库）")
    @GetMapping("v1/selectStores")
    public ComResponse<List<ImageStoreDTO>> selectStores(@RequestParam Integer type){
        return imageService.selectStores(type);
    }

}