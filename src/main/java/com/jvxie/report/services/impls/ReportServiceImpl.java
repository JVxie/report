package com.jvxie.report.services.impls;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxie.report.enums.ResponseEnum;
import com.jvxie.report.exception.ApiException;
import com.jvxie.report.exception.SystemErrorException;
import com.jvxie.report.forms.report.ReportForm;
import com.jvxie.report.forms.report.ReportListForm;
import com.jvxie.report.mappers.*;
import com.jvxie.report.models.*;
import com.jvxie.report.models.Class;
import com.jvxie.report.services.IReportService;
import com.jvxie.report.vos.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportServiceImpl implements IReportService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    AreaMapMapper areaMapMapper;

    public static final Byte SEX_MAN = (byte) 1;
    public static final Byte SEX_WOMAN = (byte) 2;
    public static final Map<Byte, String> sexNameMap = new HashMap<Byte, String>(){{
        put(SEX_MAN, "男");
        put(SEX_WOMAN, "女");
    }};

    public static final String IS_FEVER_COUGH_NO = "0";
    public static final String IS_FEVER_COUGH_FEVER = "1";
    public static final String IS_FEVER_COUGH_COUGH = "2";
    public static final String IS_FEVER_COUGH_BREATH = "3";
    public static final Map<String, String> IS_FEVER_COUGH_MAP = new HashMap<String, String>(){{
        put(IS_FEVER_COUGH_NO, "无症状");
        put(IS_FEVER_COUGH_FEVER, "有发烧症状");
        put(IS_FEVER_COUGH_COUGH, "有咳嗽症状");
        put(IS_FEVER_COUGH_BREATH, "有呼吸困难症状");
    }};

    public static final Byte IS_COVID_YES = (byte) 1;
    public static final Byte IS_COVID_NO = (byte) 0;
    public static final Map<Byte, String> IS_COVID_MAP = new HashMap<Byte, String>(){{
        put(IS_COVID_YES, "是，医院已确诊");
        put(IS_COVID_NO, "否，身体健康");
    }};

    public static final Byte IS_INFECTION_YES = (byte) 1;
    public static final Byte IS_INFECTION_NO = (byte) 0;
    public static final Map<Byte, String> IS_INFECTION_MAP = new HashMap<Byte, String>(){{
        put(IS_INFECTION_YES, "是，医院已确诊");
        put(IS_INFECTION_NO, "否，不是疑似感染者");
    }};

    public static final Byte AREA_LEVEL_HIGH = (byte) 2;
    public static final Byte AREA_LEVEL_MIDDLE = (byte) 1;
    public static final Byte AREA_LEVEL_LOW = (byte) 0;
    public static final Map<Byte, String> AREA_LEVEL_MAP = new HashMap<Byte, String>(){{
        put(AREA_LEVEL_HIGH, "高风险地区");
        put(AREA_LEVEL_MIDDLE, "中风险地区");
        put(AREA_LEVEL_LOW, "低风险地区");
    }};

    public static final Byte IS_HEALTH_CODE_YES = (byte) 1;
    public static final Byte IS_HEALTH_CODE_NO = (byte) 0;
    public static final Map<Byte, String> IS_HEALTH_CODE_MAP = new HashMap<Byte, String>(){{
        put(IS_HEALTH_CODE_YES, "是，有健康绿码");
        put(IS_HEALTH_CODE_NO, "否，没有有健康绿码");
    }};

    public static final Byte IS_BACK_FOREIGN_YES = (byte) 1;
    public static final Byte IS_BACK_FOREIGN_NO = (byte) 0;
    public static final Map<Byte, String> IS_BACK_FOREIGN_MAP =  new HashMap<Byte, String>(){{
        put(IS_BACK_FOREIGN_YES, "是，从国（境）外返回");
        put(IS_BACK_FOREIGN_NO, "否，一直在国内");
    }};

    public static final Byte IS_TOUCH_FOREIGNER_YES = (byte) 1;
    public static final Byte IS_TOUCH_FOREIGNER_NO = (byte) 0;
    public static final Map<Byte, String> IS_TOUCH_FOREIGNER_MAP = new HashMap<Byte, String>(){{
        put(IS_TOUCH_FOREIGNER_YES, "是，与国（境）外返回的人员有接触");
        put(IS_TOUCH_FOREIGNER_NO, "否，没有接触国（境）外返回的人员");
    }};

    private Boolean isAbnormalReport(Report report) {
        return (report.getTemperature().compareTo(BigDecimal.valueOf(36)) < 0)
                || (report.getTemperature().compareTo(BigDecimal.valueOf(37)) > 0)
                || !report.getIsFeverCough().equals(IS_FEVER_COUGH_NO)
                || !report.getIsCovid().equals(IS_BACK_FOREIGN_NO)
                || !report.getIsInfection().equals(IS_INFECTION_NO)
                || !report.getAreaLevel().equals(AREA_LEVEL_LOW)
                || !report.getIsHealthCode().equals(IS_HEALTH_CODE_YES)
                || !report.getIsBackForeign().equals(IS_BACK_FOREIGN_NO)
                || !report.getIsTouchForeigner().equals(IS_TOUCH_FOREIGNER_NO);
    }

    private StudentReportVo changeStudentToStudentReportVo(Student student) {
        StudentReportVo studentReportVo = new StudentReportVo();

        StudentVo studentVo = changeStudentToStudentVo(student);
        BeanUtils.copyProperties(studentVo, studentReportVo);

        Report reportToday = reportMapper.selectByNumberToday(student.getNumber());
        if (reportToday != null) {
            ReportVo reportTodayVo = changeReportToReportVo(reportToday);
            studentReportVo.setReportToday(reportTodayVo);
        }

        Report reportRecent = reportMapper.selectByNumberRecent(student.getNumber());
        if (reportRecent != null) {
            ReportVo reportRecentVo = changeReportToReportVo(reportRecent);
            studentReportVo.setReportRecent(reportRecentVo);
        }

        return studentReportVo;
    }

    private StudentVo changeStudentToStudentVo(Student student) {
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(student, studentVo);
        User user = userMapper.selectById(student.getUserId());
        if (user != null) {
            studentVo.setUserName(user.getUsername());
            String userNameId = user.getUsername() + '(' +
                    user.getId() +
                    ')';
            studentVo.setUserNameId(userNameId);
        } else {
            studentVo.setUserId(null);
        }
        Class aClass = classMapper.selectById(student.getClassId());
        if (aClass != null) {
            studentVo.setClassAbbrName(aClass.getAbbrName());
        }
        studentVo.setSexName(sexNameMap.get(student.getSex()));
        return studentVo;
    }

    private ReportVo changeReportToReportVo(Report report) {
        ReportVo reportVo = new ReportVo();
        BeanUtils.copyProperties(report, reportVo);
        Map<String, String> nowProvinceCityArea = getProvinceCityArea(report.getNowProvinceId(), report.getNowCityId(), report.getNowAreaId());
        reportVo.setNowProvinceName(nowProvinceCityArea.get("provinceName"));
        reportVo.setNowCityName(nowProvinceCityArea.get("cityName"));
        reportVo.setNowAreaName(nowProvinceCityArea.get("areaName"));
        Map<String, String> homeProvinceCityArea = getProvinceCityArea(report.getHomeProvinceId(), report.getHomeCityId(), report.getHomeAreaId());
        reportVo.setHomeProvinceName(homeProvinceCityArea.get("provinceName"));
        reportVo.setHomeCityName(homeProvinceCityArea.get("cityName"));
        reportVo.setHomeAreaName(homeProvinceCityArea.get("areaName"));
        reportVo.setIsAbnormal(isAbnormalReport(report));
        String[] isFeverCoughStrings = report.getIsFeverCough().split(",");
        Set<String> isFeverCoughSet = new HashSet<>();
        for (String isFeverCough :
                isFeverCoughStrings) {
            isFeverCoughSet.add(isFeverCough);
        }
        reportVo.setIsFeverCoughSet(isFeverCoughSet);
        List<String> stringList = Arrays.stream(isFeverCoughStrings).map(IS_FEVER_COUGH_MAP::get).collect(Collectors.toList());
        reportVo.setIsFeverCoughName(StringUtils.join(stringList, '，'));
        reportVo.setIsCovidName(IS_COVID_MAP.get(report.getIsCovid()));
        reportVo.setIsInfectionName(IS_INFECTION_MAP.get(report.getIsInfection()));
        reportVo.setAreaLevelName(AREA_LEVEL_MAP.get(report.getAreaLevel()));
        reportVo.setIsHealthCodeName(IS_HEALTH_CODE_MAP.get(report.getIsHealthCode()));
        reportVo.setIsBackForeignName(IS_BACK_FOREIGN_MAP.get(report.getIsBackForeign()));
        reportVo.setIsTouchForeignerName(IS_TOUCH_FOREIGNER_MAP.get(report.getIsTouchForeigner()));
        return reportVo;
    }

    private Map<String, String> getProvinceCityArea(Integer provinceId, Integer cityId, Integer areaId) {
        AreaMap province = areaMapMapper.selectById(provinceId);
        AreaMap city = areaMapMapper.selectById(cityId);
        AreaMap area = areaMapMapper.selectById(areaId);
        if (province == null || city == null || area == null) {
            throw new ApiException("地区错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("provinceName", province.getExtName());
        map.put("cityName", city.getExtName());
        map.put("areaName", area.getExtName());
        return map;
    }

    @Override
    public ResponseVo getStudentReport(Integer studentId) {
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            return ResponseVo.error(ResponseEnum.STUDENT_NOT_FOUND);
        }
        StudentReportVo studentReportVo = changeStudentToStudentReportVo(student);
        return ResponseVo.success(studentReportVo);
    }

    @Override
    public ResponseVo create(ReportForm form) {
        Report report = reportMapper.selectByNumberToday(form.getNumber());
        if (report != null) {
            return ResponseVo.error(ResponseEnum.REPORT_TODAY_EXISTS);
        }
        report = new Report();
        BeanUtils.copyProperties(form, report);
        int row = reportMapper.insertSelective(report);
        if (row <= 0) {
            return ResponseVo.error(ResponseEnum.SYSTEM_ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(Integer id) {
        Report report = reportMapper.selectById(id);
        if (report == null) {
            return ResponseVo.error(ResponseEnum.REPORT_TODAY_NOT_FOUND);
        }
        int row = reportMapper.deleteById(id);
        if (row <= 0) {
            throw new SystemErrorException();
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo getStudentReportList(ReportListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(pageNum, pageSize);
        List<Student> studentList = studentMapper.selectReport(form);
        List<StudentReportVo> studentReportVoList = new ArrayList<>();
        for (Student student:
             studentList) {
            StudentReportVo studentReportVo = changeStudentToStudentReportVo(student);
            studentReportVoList.add(studentReportVo);
        }
        PageInfo pageInfo = new PageInfo(studentList);
        pageInfo.setList(studentReportVoList);
        return ResponseVo.success(pageInfo);
    }

    private TeacherVo changeTeacherToTeacherVo(Teacher teacher) {
        TeacherVo teacherVo = new TeacherVo();
        BeanUtils.copyProperties(teacher, teacherVo);
        User user = userMapper.selectById(teacher.getUserId());
        if (user != null) {
            teacherVo.setUserName(user.getUsername());
            String userNameId = user.getUsername() + '(' +
                    user.getId() +
                    ')';
            teacherVo.setUserNameId(userNameId);
        } else {
            teacherVo.setUserId(null);
        }
        teacherVo.setSexName(sexNameMap.get(teacher.getSex()));
        return teacherVo;
    }

    private TeacherReportVo changeTeacherToTeacherReportVo(Teacher teacher) {
        TeacherReportVo teacherReportVo = new TeacherReportVo();
        TeacherVo teacherVo = changeTeacherToTeacherVo(teacher);
        BeanUtils.copyProperties(teacherVo, teacherReportVo);
        Report reportToday = reportMapper.selectByNumberToday(teacher.getNumber());
        if (reportToday != null) {
            ReportVo reportTodayVo = changeReportToReportVo(reportToday);
            teacherReportVo.setReportToday(reportTodayVo);
        }
        Report reportRecent = reportMapper.selectByNumberRecent(teacher.getNumber());
        if (reportRecent != null) {
            ReportVo reportRecentVo = changeReportToReportVo(reportRecent);
            teacherReportVo.setReportRecent(reportRecentVo);
        }
        return teacherReportVo;
    }

    @Override
    public ResponseVo getTeacherReportList(ReportListForm form, Integer pageNum, Integer pageSize) {
        PageHelper.clearPage();
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        List<Teacher> teacherList = teacherMapper.selectReport(form);
        List<TeacherReportVo> teacherReportVoList = teacherList.stream()
                .map(this::changeTeacherToTeacherReportVo)
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(teacherList);
        pageInfo.setList(teacherReportVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo getTeacherReport(Integer teacherId) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            return ResponseVo.error(ResponseEnum.TEACHER_NOT_FOUND);
        }
        TeacherReportVo teacherReportVo = changeTeacherToTeacherReportVo(teacher);
        return ResponseVo.success(teacherReportVo);
    }
}
