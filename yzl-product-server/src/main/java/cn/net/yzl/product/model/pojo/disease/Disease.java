package cn.net.yzl.product.model.pojo.disease;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;

/**
 * @author lichanghong
 * @version 1.0
 * @title: Disease
 * @description 病症实体
 * @date: 2021/1/9 9:18 下午
 */
@Data
public class Disease extends BaseObject {
    private int id;
    private String name;
    private int pid;
}
