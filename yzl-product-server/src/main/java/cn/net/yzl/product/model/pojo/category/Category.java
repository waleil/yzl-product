package cn.net.yzl.product.model.pojo.category;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: category
 * @description 分类
 * @date: 2021/1/9 9:58 下午
 */
@Data
public class Category extends BaseObject {
    private int id;
    private int pid;
    private String name;
}
