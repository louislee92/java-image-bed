package com.louislee92.tools.service.impl;

import com.louislee92.tools.model.ImagePO;
import com.louislee92.tools.mapper.ImageMapper;
import com.louislee92.tools.service.IImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Louis Lee
 * @since 2023-01-05
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, ImagePO> implements IImageService {

    @Autowired
    ImageMapper imageMapper;
}
