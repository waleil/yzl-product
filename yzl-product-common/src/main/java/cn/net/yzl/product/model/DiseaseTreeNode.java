package cn.net.yzl.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DiseaseTreeNode  implements Serializable {

    private Integer id;

    private String name;

    private List<DiseaseTreeNode> childeNodes;

    private List<ProductMainInfoBean> products;

}
