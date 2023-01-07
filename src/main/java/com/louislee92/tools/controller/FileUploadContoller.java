package com.louislee92.tools.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.louislee92.tools.utils.FileSizeUtil;
import com.louislee92.tools.config.SystemConfig;
import com.louislee92.tools.model.ImagePO;
import com.louislee92.tools.model.ResultVO;
import com.louislee92.tools.service.IImageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传
 */
@Slf4j
@RestController
public class FileUploadContoller {

    @Autowired
    IImageService iImageService;

    @SneakyThrows
    @PostMapping("/image/upload")
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
        log.info("成功上传文件：{}", list.stream().map(p -> p.getName()).collect(Collectors.toList()));
        return ResultVO.success();
    }

    @GetMapping("/image/urlprefix")
    public ResultVO urlPrefix() {
        return ResultVO.success(SystemConfig.INST.IMG_URL_PREFIX);
    }
}
