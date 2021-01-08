package cn.net.yzl.product.model.vo.image;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImageDTO extends BaseObject {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("url")
    private String url;

}
