package cn.net.yzl.product.model;

import cn.net.yzl.common.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseObject {
  /**
   * A JSON representation of the object.
   */
  @Override
  public String toString() {
    return JsonUtil.toJsonStr(this);
  }
}