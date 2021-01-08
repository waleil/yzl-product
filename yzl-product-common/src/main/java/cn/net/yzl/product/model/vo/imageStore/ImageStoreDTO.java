package cn.net.yzl.product.model.vo.imageStore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImageStoreDTO {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("图片库名称")
    private String name;

}
