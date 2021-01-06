package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.BaseObject;
import cn.net.yzl.product.model.vo.product.ProductDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseTreeNode
 * @description todo
 * @date: 2021/1/6 3:28 下午
 */
@Data
public class DiseaseTreeNode extends BaseObject {
    @ApiModelProperty(name = "id", value = "病症编号")
    private int id;
    @ApiModelProperty(name = "pid", value = "上级编号")
    private int pid;
    @ApiModelProperty(name = "name", value = "病症名称")
    private String name;
    @ApiModelProperty(name = "nodeList", value = "子项")
    private List<DiseaseTreeNode> nodeList;
    @ApiModelProperty(name = "productDTOList", value = "关联商品")
    private List<ProductDTO> productDTOList;
}
