package cn.net.yzl.product.sys;

import cn.net.yzl.common.enums.ResponseCodeEnums;
import lombok.Data;

@Data
public class BizException extends RuntimeException {
     /**
       * 错误码
    */
    protected Integer code;
    /**
       * 错误信息
    */
    protected String message;
    public BizException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(ResponseCodeEnums responseCodeEnums) {
        this.code = responseCodeEnums.getCode();
        this.message = responseCodeEnums.getMessage();
    }
}