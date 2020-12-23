package com.jvxie.report.services;

import com.jvxie.report.vos.ResponseVo;

public interface IAreaMapService {
    ResponseVo getAll();

    ResponseVo show(Integer id);
}
