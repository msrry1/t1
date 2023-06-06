package com.zsxb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsxb.common.CommonDict;
import com.zsxb.common.JsonResult;
import com.zsxb.config.RootInfo;
import com.zsxb.po.Customer;
import com.zsxb.service.CustomerService;
import com.zsxb.util.JwtUtil;
import com.zsxb.vo.LoginReturnVO;
import com.zsxb.vo.LoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 顾客操作控制层
 *
 * @author dz
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RootInfo rootInfo;
    /**
     * 顾客登录
     * @param loginVO 登录的vo
     * @return  登录的顾客和token
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
        Customer customer = customerService.login(loginVO.getUsername(),
                loginVO.getPassword());

        LoginReturnVO loginReturnVO = new LoginReturnVO();
        // 根据顾客前缀和id创建token
        String token = JwtUtil.createJWT(CommonDict.CUSTOMER_TOKENKEY + customer.getCusId(),
                CommonDict.TOKEN_EXPIRE_TIME);

        // 设置token
        loginReturnVO.setToken(token);

        return JsonResult.ok(loginReturnVO);
    }



    /**
     * 分页查询顾客
     * @param current 当前页
     * @param size 每页显示记录数
     * @param cusUid 顾客用户名（可为空）
     * @return
     */
    @PostMapping("/page/{current}/{size}")
    public JsonResult<Page> page(@PathVariable int current,
                                 @PathVariable int size,
                                 @RequestParam(required = false) String cusUid) {
        Page<Customer> page = new Page<>(current, size);
        customerService.queryPage(page, cusUid);
        return JsonResult.ok(page);
    }


    /**
     * 添加顾客
     * @param customer 新增的顾客信息
     * @return
     */
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Customer customer) {

        customerService.add(customer);

        return JsonResult.ok();
    }


    /**
     * 删除顾客
     * @param cusId 顾客id
     * @return
     */
    @PostMapping("/delete")
    public JsonResult<Void> delete(int cusId) {

        customerService.delete(cusId);

        return JsonResult.ok();
    }


    /**
     * 修改顾客
     * @param customer 修改后的顾客信息
     * @return
     */
    @PostMapping("/update")
    public JsonResult<Void> update(@RequestBody Customer customer) {
        customerService.update(customer);

        return JsonResult.ok();
    }


    /**
     * 顾客注册
     * @param loginVO
     * @return
     */
    @PostMapping("/register")
    public JsonResult<Void> register(@RequestBody LoginVO loginVO) {
        customerService.register(loginVO.getUsername(), loginVO.getPassword());
        return JsonResult.ok();
    }

}
