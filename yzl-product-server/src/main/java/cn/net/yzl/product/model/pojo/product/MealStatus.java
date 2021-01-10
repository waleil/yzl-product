package cn.net.yzl.product.model.pojo.product;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * @author wanghuasheng
 * @version 1.0
 * @title: MealStatus
 * @description 套餐状态
 * @date: 2021/1/10 15:35 下午
 */
@Data
public class MealStatus extends BaseObject {
    //套餐ID唯一编号
    private Long mealNo;
    private Date updateTime;
}
