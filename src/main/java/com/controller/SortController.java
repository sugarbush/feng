package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.pojo.Sort;
import com.service.SortService;
import com.util.ResponseObj;
import com.util.SFTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: feng
 * @Date: 2019/9/18 14:32
 */
@Controller
@RequestMapping("/")
public class SortController {

    @Autowired
    private SortService sortService;
    ResponseObj responseObj;


    /**
     * 根据查询条件查询分类列表(分页）
     * @param sort
     * @return
     */
    @RequestMapping("/getSortAllList")
    @ResponseBody
    public PageInfo getSortAllList(Sort sort,HttpServletRequest request){
        //获取前台当前页
        String currentPage = request.getParameter("pageno");
        //获取前台每页显示数据
        String pageSizess = request.getParameter("pagesize");

        String sortName = request.getParameter("sortName");
        String dStatus = request.getParameter("dStatus");

        int pageNum =1;
        int pageSize =10;
        if(currentPage == null){
            pageNum =1;
        }else{
            pageNum = Integer.parseInt(currentPage);
        }
        if(pageSizess == null){
            pageNum =10;
        }else{
            pageSize =  Integer.parseInt(pageSizess);
        }
        /*分页*/
        PageHelper.startPage(pageNum, pageSize );
        //调用service方法
        sort.setSortName(sortName);
        sort.setdStatus(dStatus);
        List<Sort> list =  sortService.getSortAllList(sort);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 查询所有分类信息列表
     * @param sort
     * @return
     */
    @RequestMapping("/querySortAllList")
    @ResponseBody
    public PageInfo querySortAllList(Sort sort,HttpServletRequest request){ 
        List<Sort> list =  sortService.getSortAllList(sort);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }
    
    /**
     * 新增分类
     * @param sort
     * @return
     */
    @RequestMapping(value = "/addSort" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSort(Sort sort) {
        Map<String, Object> map = new HashMap<>();
        sort.setCreateBy("admin");
        sort.setUpdateBy("admin");
        int count = sortService.addSort(sort);
        if(count >0) {
            map.put("errorCode", "000000");
            map.put("errorMsg", "新增分类成功！");
        }else{
            map.put("errorCode", "000001");
            map.put("errorMsg", "新增分类失败！");
        }
        return map;
    }

    /**
     * 根据tid查询分类详细信息
     * @param sort
     * @return
     */
    @RequestMapping("/getSortInfo")
    @ResponseBody
    public Map<String, Object> getSortInfo(Sort sort){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer tId = sort.gettId();
        if(tId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            Sort sort1 = sortService.getSortInfo(tId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "根据sid查询分类详细信息成功!");
            map.put("sort",sort1);
        }
        return  map;
    }

    /**
     * 通过TID修改分类信息
     * @param sort
     * @return
     */
    @RequestMapping(value="/updateSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSort(Sort sort){
        Map<String, Object> map = new HashMap<String, Object>();

        Integer tId = sort.gettId();
        sort.setUpdateBy("admin");
        if(tId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            int sortSize = sortService.updateSort(sort);
            if(sortSize >0) {
                map.put("errorCode", "000000");
                map.put("errorMsg", "修改分类成功！");
            }else{
                map.put("errorCode", "000001");
                map.put("errorMsg", "修改分类失败！");
            }
        }
        return  map;
    }
    /**
     * 通过SID删除分类信息
     * @param sort
     * @return
     */
    @RequestMapping(value="/delSort", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delSort(Sort sort,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer tId =Integer.parseInt(request.getParameter("tId"));
        if(tId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            sortService.delSort(tId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "删除分类成功！");
        }
        return  map;
    }

}
