package com.powernode.controller;

import com.github.pagehelper.PageInfo;
import com.powernode.bean.Goods;
import com.powernode.bean.Photo;
import com.powernode.service.IGoodsService;
import com.powernode.service.IPhotoService;
import com.powernode.util.MessageUtils;
import com.powernode.util.ResultObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/good")
public class GoodsController {
    @Autowired
    private IGoodsService goodsServiceImpl;
    @Autowired
    private IPhotoService photoServiceImpl;
    @RequestMapping("/saveGoods.action")
    public ResultObjectUtils saveGoods(Photo photo,HttpServletRequest request, MultipartFile[] files, Goods goods, ResultObjectUtils resultObjectUtils) throws IOException {
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        if (files!=null){
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
                String replace = UUID.randomUUID().toString().replace("-", "");
                String fileName=replace+substring;
                photo.setPhotoId(replace1);
                photo.setPhotoName(fileName);
                photoServiceImpl.addPhoto(photo);
                ServletContext servletContext = request.getServletContext();
                String realPath = servletContext.getRealPath("/upload");
                File file1 = new File(realPath + "/" + fileName);
                file.transferTo(file1);
            }
            goods.setGoodsStatus(1);
            goods.setPhotoId(replace1);
            boolean flag=goodsServiceImpl.saveGoods(goods);
            if (flag){
                resultObjectUtils.setResultCode(MessageUtils.SUCCESS_CODE);
                resultObjectUtils.setResultMsg(MessageUtils.SUCCESS_MSG);
            }else {
                resultObjectUtils.setResultCode(MessageUtils.FAIL_CODE);
                resultObjectUtils.setResultMsg(MessageUtils.FAIL_MSG);
            }
            return resultObjectUtils;
        }
        return null;
    }

    @RequestMapping("/queryGoods.action")
    public PageInfo<Goods> queryGoods(@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum){
        System.out.println("pageSize = " + pageSize);
        System.out.println("pageNum = " + pageNum);
        List<Goods> goodsList=goodsServiceImpl.queryGoods(pageSize,pageNum);
        //分页插件  把分页产生的所有信息都封装到PageInfo对象中
        PageInfo pageInfo = new PageInfo(goodsList);
        //pageInfo中有  pageNum  pageSize  startIndex  total  pages  list  上一页  下一页  beginIndex  endIndex
        System.out.println("pageInfo = " + pageInfo);
        return pageInfo;
    }

}
