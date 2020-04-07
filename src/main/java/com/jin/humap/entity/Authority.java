package com.jin.humap.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Authority implements Serializable {
    private Long id;

    private Integer type;

    private Long userId;

    private String authority;

    private String authorityName;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

}