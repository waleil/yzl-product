package cn.net.yzl.product.model.vo.product.dto;

import cn.net.yzl.product.model.db.ProductAtlasBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "ProductAtlasDto",description = "商品图谱集合")
public class ProductAtlasDTO {

    /**
     * 大list   存储横轴 同一病症 不同商品品牌+商品名称前4字的数据
     * map     key：1代表自营   2代表第三方
     *小list    存储纵轴商品数据
     */
    @ApiModelProperty(value = "商品图谱集合",name = "productAtlasResult")
    List<Map<Integer, List<ProductAtlasBean>>> productAtlasResult;
}
