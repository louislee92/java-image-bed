package com.louislee92.tools.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Louis Lee
 * @since 2023-01-05
 */
@Getter
@Setter
@TableName("image")
public class ImagePO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String path;

    private String size;

    private String time;


}
