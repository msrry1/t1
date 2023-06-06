package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.common.JsonResult;
import com.zsxb.config.RootInfo;
import com.zsxb.po.Employee;
import com.zsxb.service.EmployeeService;
import com.zsxb.util.JwtUtil;
import com.zsxb.vo.LoginReturnVO;
import com.zsxb.vo.LoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 管理员操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RootInfo rootInfo;

    /**
     * 管理员登录
     * @param loginVO 登录的vo
     * @return  登录的管理员和token
     */
    @PostMapping("/login")
    public JsonResult<LoginReturnVO> login(@RequestBody LoginVO loginVO) {

//         如果是root用户，生成root用户的token，直接返回root用户
        if (rootInfo.isRoot(loginVO.getUsername(), loginVO.getPassword())) {
            LoginReturnVO root = new LoginReturnVO();
            String token = JwtUtil.createJWT("root",
                    CommonDict.TOKEN_EXPIRE_TIME);
            root.setToken(token);
            return JsonResult.ok(root);
        }

        // 默认用户名密码登录
        Employee employee = employeeService.login(loginVO.getUsername(),
                loginVO.getPassword());

        LoginReturnVO employeeReturnVO = new LoginReturnVO();
        // 根据管理员前缀和id创建token
        String token = JwtUtil.createJWT(CommonDict.EMPLOYEE_TOKENKEY + employee.getEmpId(),
                CommonDict.TOKEN_EXPIRE_TIME);

        // 设置token
        employeeReturnVO.setToken(token);

        return JsonResult.ok(employeeReturnVO);
    }


    /**
     * 分页查询管理员
     * @param current   当前页
     * @param size  每页显示个数
     * @param empUid    管理员用户名（可为null）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String empUid) {
        // 创建分页对象
        Page page = new Page(current, size);
        // 查询分页对象，查询完成后employee集合会放在page.records属性里
        employeeService.queryPage(page, empUid);

        // 返回page对象
        return JsonResult.ok(page);
    }

    /**
     * 删除管理员
     * @param empId 管理员id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int empId) {

        // 删除管理员
        employeeService.delete(empId);

        return JsonResult.ok();
    }

    /**
     * 修改管理员
     * @param employee
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Employee employee) {

        employeeService.update(employee);

        return JsonResult.ok();
    }

    /**
     * 添加管理员
     * @param employee
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Employee employee) {

        employeeService.add(employee);

        return JsonResult.ok();
    }



}
