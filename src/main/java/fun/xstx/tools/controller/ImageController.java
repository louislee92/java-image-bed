package fun.xstx.tools.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.xstx.tools.config.SystemConfig;
import fun.xstx.tools.model.ImagePO;
import fun.xstx.tools.model.ImageQO;
import fun.xstx.tools.model.ResultVO;
import fun.xstx.tools.service.IImageService;
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
        if(StrUtil.isNotBlank(qo.getKeyword())) queryWrapper.like("name", qo.getKeyword());
        if(StrUtil.isNotBlank(qo.getId())) queryWrapper.eq("id", qo.getId());
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
            FileUtil.del(StrUtil.format("{}{}", SystemConfig.INST.IMG_DIR, realImagePO.getPath()));
            iImageService.removeById(id);
        }
        return ResultVO.success();
    }





}
