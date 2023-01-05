package fun.xstx.tools.service.impl;

import cn.hutool.core.util.StrUtil;
import fun.xstx.tools.config.SystemConfig;
import fun.xstx.tools.model.ImagePO;
import fun.xstx.tools.mapper.ImageMapper;
import fun.xstx.tools.service.IImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
