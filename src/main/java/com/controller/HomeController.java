package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.pojo.Home;
import com.service.HomeService;
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
public class HomeController {

    @Autowired
    private HomeService HomeService;
    ResponseObj responseObj;

    String basePath = "/app/sftpUpload";
    String directory ="jr_system";

    /**
     * 根据查询条件查询首页信息列表
     * @param home
     * @return
     */
    @RequestMapping("/getHomeAllList")
    @ResponseBody
    public PageInfo getHomeAllList(Home home,HttpServletRequest request){
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
        home.setTitle(title);
        home.setSubtitle(subTitle);
        home.setPubDate(pubDate);
        List<Home> list =  HomeService.getHomeAllList(home);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 新增首页信息
     * @param home
     * @return
     */
    @RequestMapping(value = "/addHome" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addHome(@RequestParam(value="codeUrl",required = false) MultipartFile file,Home home) {
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
        //------保存首页信息相关信息
        home.setCreateBy("admin");
        home.setUpdateBy("admin");
        home.setTitFileName(fileName);
        home.setTitFileNameSf(sftpFileName);
        home.setTitFilePath(basePath+"/"+directory);
        int count = HomeService.addHome(home);
        if(count >0) {
            map.put("errorCode", "000000");
            map.put("errorMsg", "新增首页信息成功！");
        }else{
            map.put("errorCode", "000001");
            map.put("errorMsg", "新增首页信息失败！");
        }
        return map;
    }

    /**
     * 图片回显示
     * @param req
     * @param response
     */
    @RequestMapping("/downloadHomeFile")
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
     * 根据sid查询首页信息详细信息
     * @param home
     * @return
     */
    @RequestMapping("/getHomeInfo")
    @ResponseBody
    public Map<String, Object> getHomeInfo(Home home){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer hId = home.gethId();
        if(hId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            Home home1 = HomeService.getHomeInfo(hId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "根据sid查询首页信息详细信息成功!");
            map.put("Home",home1);
        }
        return  map;
    }

    /**
     * 通过HID修改首页信息信息
     * @param home
     * @return
     */
    @RequestMapping(value="/updateHome", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateHome(@RequestParam(value="codeUrl",required = false) MultipartFile file,Home home){
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

        Integer hId = home.gethId();
        home.setUpdateBy("admin");
        home.setTitFileName(fileName);
        home.setTitFileNameSf(sftpFileName);
        home.setTitFilePath(basePath+"/"+directory);
        if(hId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            int HomeSize = HomeService.updateHome(home);
            if(HomeSize >0) {
                map.put("errorCode", "000000");
                map.put("errorMsg", "修改首页信息成功！");
            }else{
                map.put("errorCode", "000001");
                map.put("errorMsg", "修改首页信息失败！");
            }
        }
        return  map;
    }
    /**
     * 通过SID删除首页信息信息
     * @param
     * @return
     */
    @RequestMapping(value="/delHome", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delHome(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer sId =Integer.parseInt(request.getParameter("sId"));
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            HomeService.delHome(sId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "删除首页信息成功！");
        }
        return  map;
    }

}
