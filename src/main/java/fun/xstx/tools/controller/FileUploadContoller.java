package fun.xstx.tools.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import fun.xstx.tools.config.SystemConfig;
import fun.xstx.tools.model.ImagePO;
import fun.xstx.tools.model.ResultVO;
import fun.xstx.tools.service.IImageService;
import fun.xstx.tools.utils.FileSizeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 */
@Slf4j
@RestController
@RequestMapping("/image/upload")
public class FileUploadContoller {

    @Autowired
    IImageService iImageService;

    @SneakyThrows
    @PostMapping
    public ResultVO upload(MultipartFile[] files) {
        log.info("文件个数：{}",files.length);
        List<ImagePO> list = new ArrayList<>();
        for(MultipartFile file : files) {
            // 将文件存储到指定位置
            String name = file.getOriginalFilename();
            String type = FileUtil.extName(name);
            String id = RandomUtil.randomString(15);
            String path = StrUtil.format("/{}.{}", id, type);
            String fileSystemPath = StrUtil.format("{}{}", SystemConfig.INST.IMG_DIR, path);
            FileUtil.writeBytes(file.getBytes(), fileSystemPath);
            // 图片元信息存储到数据库
            ImagePO imagePO = new ImagePO();
            imagePO.setId(id);
            imagePO.setTime(DateUtil.now());
            imagePO.setName(name);
            imagePO.setPath(path);
            imagePO.setSize(FileSizeUtil.readableFileSize(file.getSize()));
            list.add(imagePO);
        }
        iImageService.saveBatch(list);
        return ResultVO.success();
    }
}
