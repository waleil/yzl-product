package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductStatusCountDTO
 * @description 商品上下架统计
 * @date: 2021/1/7 9:55 下午
 */
@Data
public class ProductStatusCountDTO extends BaseObject {
    @ApiModelProperty(name = "status", value = "0代表下架，1代表上架")
    private Integer status;
    @ApiModelProperty(name = "cnt", value = "商品总数")
    private int cnt;
}
