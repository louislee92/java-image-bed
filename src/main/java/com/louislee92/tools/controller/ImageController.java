package com.louislee92.tools.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.louislee92.tools.model.ImageQO;
import com.louislee92.tools.config.SystemConfig;
import com.louislee92.tools.model.ImagePO;
import com.louislee92.tools.model.ResultVO;
import com.louislee92.tools.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Louis Lee
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    IImageService iImageService;

    /**
     * 新增图片对象
     * @param imagePO
     * @return
     */
    @PostMapping
    public ResultVO create(@RequestBody ImagePO imagePO) {
        imagePO.setId(RandomUtil.randomString(15));
        imagePO.setTime(DateUtil.now());
        iImageService.save(imagePO);
        return ResultVO.success();
    }

    /**
     * 图片查询
     * @param qo
     * @return
     */
    @GetMapping
    public ResultVO read(ImageQO qo) {
        if(qo.getCurrent() == 0) qo.setCurrent(1);
        if(qo.getSize() == 0) qo.setSize(100);
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StrUtil.isNotBlank(qo.getKeyword())) {
            queryWrapper.eq("id", qo.getKeyword());
            queryWrapper.or();
            queryWrapper.like("name", qo.getKeyword());
        }
        queryWrapper.orderByDesc("time");
        Page<ImagePO> page = new Page<>();
        page.setCurrent(qo.getCurrent());
        page.setSize(qo.getSize());
        return ResultVO.success(iImageService.page(page, queryWrapper));
    }

    /**
     * 图片批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResultVO delete(@RequestBody String[] ids) {
        for(String id : ids) {
            ImagePO realImagePO = iImageService.getById(id);
            String path = StrUtil.format("{}{}", SystemConfig.INST.IMG_DIR, realImagePO.getPath());
            if(FileUtil.exist(path)) FileUtil.del(path);
            iImageService.removeById(id);
        }
        return ResultVO.success();
    }





}
