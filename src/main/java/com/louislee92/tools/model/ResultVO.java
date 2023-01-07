package com.louislee92.tools.model;

import com.louislee92.tools.consts.ResultCodeConst;
import lombok.Data;

@Data
public class ResultVO {
    private int code;
    private Object data;
    private String message;

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO success(Object data) {
        ResultVO vo = new ResultVO();
        vo.setData(data);
        vo.setCode(ResultCodeConst.SUCCESS);
        return vo;
    }

    public static ResultVO fail() {
        return fail("操作失败！");
    }
    public static ResultVO fail(String message) {
        ResultVO vo = new ResultVO();
        vo.setCode(ResultCodeConst.FAIL);
        vo.setMessage(message);
        return vo;
    }
}
