package cn.net.yzl.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
/*
 * @description 提供给前台的树状map封装类
 * @author Majinbao
 * @date 2020/12/25 10:04
 */
public class CategoryTreeNode  implements Serializable {

    private Integer id;//分类id

    private String name;//分类名称

    private List<CategoryTreeNode> childNodes;//子类

}
