package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.pojo.Dynamic;
import com.service.DynamicService;
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
 * @Date: 2019/9/24 14:32
 */
@Controller
@RequestMapping("/")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;
    ResponseObj responseObj;

    String basePath = "/app/sftpUpload";
    String directory ="jr_system";

    /**
     * 根据查询条件查询公司动态列表_分页
     * @param dynamic
     * @return
     */
    @RequestMapping("/getDynamicAllList")
    @ResponseBody
    public PageInfo getDynamicAllList(Dynamic dynamic,HttpServletRequest request){
        //获取前台当前页
        String currentPage = request.getParameter("pageno");
        //获取前台每页显示数据
        String pageSizess = request.getParameter("pagesize");

        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String pubDate = request.getParameter("pubDate");

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
        dynamic.setTitle(title);
        dynamic.setSubtitle(subTitle);
        dynamic.setPubDate(pubDate);
        List<Dynamic> list =  dynamicService.getDynamicAllList(dynamic);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 根据查询条件查询公司动态列表
     * @param dynamic
     * @return
     */
    @RequestMapping("/queryDynamicAllList")
    @ResponseBody
    public PageInfo queryDynamicAllList(Dynamic dynamic,HttpServletRequest request){
        //获取前台当前页
        String currentPage = request.getParameter("pageno");
        //获取前台每页显示数据
        String pageSizess = request.getParameter("pagesize");

        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String pubDate = request.getParameter("pubDate");

        //调用service方法
        dynamic.setTitle(title);
        dynamic.setSubtitle(subTitle);
        dynamic.setPubDate(pubDate);
        List<Dynamic> list =  dynamicService.getDynamicAllList(dynamic);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 新增公司动态
     * @param dynamic
     * @return
     */
    @RequestMapping(value = "/addDynamic" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDynamic(@RequestParam(value="codeUrl",required = false) MultipartFile file,Dynamic dynamic) {
        Map<String, Object> map = new HashMap<>();
        String fileName  = file.getOriginalFilename();//得到真实的文件名
        String lastName  = fileName.substring(fileName.lastIndexOf("."));//获得文件的后缀
        String sftpFileName = UUID.randomUUID().toString() + lastName;

        SFTPUtil sftp = new SFTPUtil("sftpUser", "sftpUser123", "47.106.195.175", 22);
        try {
              sftp.login();
              InputStream is =  file.getInputStream();//按此可以得到流
              sftp.upload(basePath,directory, sftpFileName, is);
              sftp.logout();
        } catch (IOException | SftpException e) {
            sftp.logout();
            map.put("errorCode","500");
            map.put("errorMsg","文件保存失败！");
        }finally {
            sftp.logout();
        }
        //------保存公司动态相关信息
        dynamic.setCreateBy("admin");
        dynamic.setUpdateBy("admin");
        dynamic.setTitFileName(fileName);
        dynamic.setTitFileNameSf(sftpFileName);
        dynamic.setTitFilePath(basePath+"/"+directory);
        int count = dynamicService.addDynamic(dynamic);
        if(count >0) {
            map.put("errorCode", "000000");
            map.put("errorMsg", "新增公司动态成功！");
        }else{
            map.put("errorCode", "000001");
            map.put("errorMsg", "新增公司动态失败！");
        }
        return map;
    }

    /**
     * 图片回显示
     * @param req
     * @param response
     */
    @RequestMapping("/downloadDynamicFile")
    public void downLoadFile(HttpServletRequest req, HttpServletResponse response){
        String fileName = req.getParameter("fileName");
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        byte[] buffer = new byte[1024 * 10];
        InputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream out = null;

        SFTPUtil sftp = new SFTPUtil("sftpUser", "sftpUser123", "47.106.195.175", 22);

        try {
            out = response.getOutputStream();

            sftp.login();
            InputStream inputStr = sftp.downloadHx("/app/sftpUpload/jr_system" , fileName);

            //读取文件流
            int len = 0;
            while ((len = inputStr.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            //logger.error(e);
        } finally {
            sftp.logout();
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    bis = null;
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    fis = null;
                }
            }
        }
    }
    /**
     * 根据sid查询公司动态详细信息
     * @param dynamic
     * @return
     */
    @RequestMapping("/getDynamicInfo")
    @ResponseBody
    public Map<String, Object> getDynamicInfo(Dynamic dynamic){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer dId = dynamic.getdId();
        if(dId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            Dynamic Dynamic1 = dynamicService.getDynamicInfo(dId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "根据sid查询公司动态详细信息成功!");
            map.put("dynamic",Dynamic1);
        }
        return  map;
    }

    /**
     * 通过SID修改公司动态信息
     * @param dynamic
     * @return
     */
    @RequestMapping(value="/updateDynamic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDynamic(@RequestParam(value="codeUrl",required = false) MultipartFile file,Dynamic dynamic){
        Map<String, Object> map = new HashMap<String, Object>();
        String fileName  = file.getOriginalFilename();//得到真实的文件名
        String lastName  = fileName.substring(fileName.lastIndexOf("."));//获得文件的后缀
        String sftpFileName = UUID.randomUUID().toString() + lastName;

        SFTPUtil sftp = new SFTPUtil("sftpUser", "sftpUser123", "47.106.195.175", 22);
        try {
            sftp.login();
            InputStream is =  file.getInputStream();//按此可以得到流
            sftp.upload(basePath,directory, sftpFileName, is);
            sftp.logout();
        } catch (IOException | SftpException e) {
            sftp.logout();
            map.put("errorCode","500");
            map.put("errorMsg","文件保存失败！");
        }finally {
            sftp.logout();
        }

        Integer sId = dynamic.getdId();
        dynamic.setUpdateBy("admin");
        dynamic.setTitFileName(fileName);
        dynamic.setTitFileNameSf(sftpFileName);
        dynamic.setTitFilePath(basePath+"/"+directory);
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            int dynamicSize = dynamicService.updateDynamic(dynamic);
            if(dynamicSize >0) {
                map.put("errorCode", "000000");
                map.put("errorMsg", "修改公司动态成功！");
            }else{
                map.put("errorCode", "000001");
                map.put("errorMsg", "修改公司动态失败！");
            }
        }
        return  map;
    }
    /**
     * 通过SID删除公司动态信息
     * @param dynamic
     * @return
     */
    @RequestMapping(value="/delDynamic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDynamic(Dynamic dynamic,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer dId =Integer.parseInt(request.getParameter("dId"));
        if(dId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            dynamicService.delDynamic(dId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "删除公司动态成功！");
        }
        return  map;
    }

}
