package cn.net.yzl.product.model.vo.product.vo;

import cn.net.yzl.product.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wujianing
 * @version 1.0
 * @title: ProductMealVO
 * @description 商品套餐列表查询条件
 * @date: 2021/1/9 11:39 上午
 */
@Data
public class ProductMealVO extends BaseObject {

    @ApiModelProperty(name = "keyword", value = "关键词搜索/针对商品套餐名称和编码")
    private String keyword;

    @ApiModelProperty(name = "pageNo", value = "分页页码")
    private Integer pageNo;

    @ApiModelProperty(name = "pageSize", value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(name = "priceDown", value = "价格区间下限")
    private Double priceDown;
    @ApiModelProperty(name = "priceDown", value = "价格区间上限")
    private Double priceUp;
    @JsonIgnore
    private Integer downPrice;

    @JsonIgnore
    private Integer upPrice;

    @ApiModelProperty(name = "diseaseId", value = "商品主治病症")
    private Integer diseaseId;

    @ApiModelProperty(name = "status", value = "上下架状态：0代表下架，1代表上架，null代表全部")
    private Integer status;
}
