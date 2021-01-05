package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.db.ProductMainInfoBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DiseaseTreeNode  implements Serializable {

    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;

    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    @ApiModelProperty(name = "childeNodes", value = "子节点")
    private List<DiseaseTreeNode> childeNodes;

    private List<ProductMainInfoBean> products;

}
