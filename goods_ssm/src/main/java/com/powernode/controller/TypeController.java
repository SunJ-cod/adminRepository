package com.powernode.controller;

import com.powernode.bean.Type;
import com.powernode.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private ITypeService typeServiceImpl;
    @RequestMapping("/queryType.action")
    public List<Type> queryType(){
        List<Type> typeList=typeServiceImpl.queryType();
        return typeList;
    }
}
