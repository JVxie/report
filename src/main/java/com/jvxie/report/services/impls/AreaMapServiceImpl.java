package com.jvxie.report.services.impls;

import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.mappers.AreaMapMapper;
import com.jvxie.report.models.AreaMap;
import com.jvxie.report.services.IAreaMapService;
import com.jvxie.report.vos.AreaMapVo;
import com.jvxie.report.vos.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaMapServiceImpl implements IAreaMapService {
    private static final Integer ROOT_PID = 0;
    private static final Integer DEEP_MAX = 2;

    @Autowired
    AreaMapMapper areaMapMapper;

    @Override
    public ResponseVo getAll() {
        List<AreaMapVo> areaMapVoList = dfsAreaMapByPid(ROOT_PID);
        return ResponseVo.success(areaMapVoList);
    }

    private List<AreaMapVo> dfsAreaMapByPid(Integer pid) {
        List<AreaMap> areaMapList = areaMapMapper.selectByPid(pid);
        List<AreaMapVo> areaMapVoList = areaMapList.stream().map(this::changeAreaMapToAreaMapVo).collect(Collectors.toList());
        for (AreaMapVo areaMapVo:
                areaMapVoList) {
            if (areaMapVo.getDeep() >= DEEP_MAX) {
                continue;
            }
            List<AreaMapVo> mapVoList = dfsAreaMapByPid(areaMapVo.getId());
            areaMapVo.setList(mapVoList);
        }
        return areaMapVoList;
    }

    private AreaMapVo changeAreaMapToAreaMapVo(AreaMap areaMap) {
        AreaMapVo areaMapVo = new AreaMapVo();
        BeanUtils.copyProperties(areaMap, areaMapVo);
        return areaMapVo;
    }

    @Override
    public ResponseVo show(Integer id) {
        AreaMap areaMap = areaMapMapper.selectById(id);
        if (areaMap == null) {
            return ResponseVo.error(ResponseEnum.AREA_MAP_NOT_FOUND);
        }
        AreaMapVo areaMapVo = changeAreaMapToAreaMapVo(areaMap);
        return ResponseVo.success(areaMapVo);
    }
}
