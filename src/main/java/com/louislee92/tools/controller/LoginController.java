package com.louislee92.tools.controller;

import com.louislee92.tools.config.SystemConfig;
import com.louislee92.tools.model.LoginQO;
import com.louislee92.tools.model.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    /**
     * 登录口令认证
     * @param qo
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginQO qo) {
        if(!SystemConfig.INST.IMG_CODE.equals(qo.getCode()))
            throw new RuntimeException("口令错误");
        return ResultVO.success();
    }
}
