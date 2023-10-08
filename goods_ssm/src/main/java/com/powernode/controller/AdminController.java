package com.powernode.controller;

import com.powernode.bean.Admin;
import com.powernode.service.IAdminService;
import com.powernode.util.MessageUtils;
import com.powernode.util.ResultObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @RequestMapping("/regist.action")
    public ResultObjectUtils regist(Admin admin,ResultObjectUtils resultObjectUtils){
        boolean flag=adminService.add(admin);
        if (flag){
            resultObjectUtils.setResultCode(MessageUtils.SUCCESS_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.SUCCESS_MSG);
        }else {
            resultObjectUtils.setResultCode(MessageUtils.FAIL_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.FAIL_MSG);
        }
        return resultObjectUtils;
    }
    @RequestMapping("/login.action")
    public ResultObjectUtils login(Admin admin, HttpSession session,ResultObjectUtils resultObjectUtils){
        Admin admin1=adminService.query(admin);
        if (admin1!=null){
            resultObjectUtils.setResultCode(MessageUtils.SUCCESS_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.SUCCESS_MSG);
            session.setAttribute("admin",admin1);
        }else {
            resultObjectUtils.setResultCode(MessageUtils.FAIL_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.FAIL_MSG);
            resultObjectUtils.putDataToMap("message","账号或密码错误");
        }
        return resultObjectUtils;
    }
    @RequestMapping("/main.action")
    public ResultObjectUtils mainAdmin(HttpSession session,ResultObjectUtils resultObjectUtils){
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin!=null){
            resultObjectUtils.setResultCode(MessageUtils.SUCCESS_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.SUCCESS_MSG);
            resultObjectUtils.putDataToMap("admin",admin);
        }else {
            resultObjectUtils.setResultCode(MessageUtils.FAIL_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.FAIL_MSG);

        }
        return resultObjectUtils;
    }
    @RequestMapping("/logout.action")
    public ResultObjectUtils logout(HttpSession session,ResultObjectUtils resultObjectUtils){
        session.removeAttribute("admin");
        Object admin = session.getAttribute("admin");
        if (admin!=null){
            resultObjectUtils.setResultCode(MessageUtils.FAIL_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.FAIL_MSG);
        }else {
            resultObjectUtils.setResultCode(MessageUtils.SUCCESS_CODE);
            resultObjectUtils.setResultMsg(MessageUtils.SUCCESS_MSG);
            resultObjectUtils.putDataToMap("message","退出失败！");
        }
        return resultObjectUtils;
    }
}
