package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.pojo.SortSublist;
import com.service.SortSublistService;
import com.util.Msg;
import com.util.ResponseObj;
import com.util.SFTPUtil;
import org.apache.commons.io.FileUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: feng
 * @Date: 2019/9/24 14:32
 */
@Controller
@RequestMapping("/")
public class SortSublistController {

    @Autowired
    private SortSublistService sortSublistService;
    ResponseObj responseObj;

    String basePath = "/app/sftpUpload";
    String directory ="jr_system";

    /**
     * 根据查询条件查询关于我们子列表列表
     * @param sortSublist
     * @return
     */
    @RequestMapping("/getSortSublistAllList")
    @ResponseBody
    public PageInfo getSortSublistAllList(SortSublist sortSublist,HttpServletRequest request){
        //获取前台当前页
        String currentPage = request.getParameter("pageno");
        //获取前台每页显示数据
        String pageSizess = request.getParameter("pagesize");

        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String pubDate = request.getParameter("pubDate");
        String sortId = request.getParameter("sortId");

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
        sortSublist.setTitle(title);
        sortSublist.setSubtitle(subTitle);
        sortSublist.setPubDate(pubDate);
        sortSublist.setSortId(sortId);
        List<SortSublist> list =  sortSublistService.getSortSublistAllList(sortSublist);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 富文本编辑器图片上传
     * @param request
     * @param multipartFile
     */
    @RequestMapping(value = "/commodityUpload", method = RequestMethod.POST)
    @ResponseBody
    public Msg commodityUpload(HttpServletRequest request,@RequestParam("myFile") MultipartFile multipartFile) {
        try {
            // 获取项目路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            InputStream inputStream = multipartFile.getInputStream();
            String contextPath = request.getContextPath();
            // 服务器根目录的路径
            String path = realPath.replace(contextPath.substring(1), "");
            // 根目录下新建文件夹upload，存放上传图片
            String uploadPath = path + "upload";
            // 获取文件名称
            String filename = getFileName();
            // 将文件上传的服务器根目录下的upload文件夹
            File file = new File(uploadPath, filename);
            FileUtils.copyInputStreamToFile(inputStream, file);
            // 返回图片访问路径
            String url = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/upload/" + filename;

            String[] str = { url };
            Msg we = new Msg(str);
            return we;
        } catch (IOException e) {
            //log.error("上传文件失败", e);
        }
        return null;
    }

    public String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String str = UUID.randomUUID().toString();
        String name = timeStr + str + ".jpg";
        return name;
    }

    /**
     * 新增关于我们子列表
     * @param sortSublist
     * @return
     */
    @RequestMapping(value = "/addSortSublist" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSortSublist(@RequestParam(value="codeUrl",required = false) MultipartFile file,SortSublist sortSublist) {
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
        //------保存关于我们子列表相关信息
        sortSublist.setCreateBy("admin");
        sortSublist.setUpdateBy("admin");
        sortSublist.setTitFileName(fileName);
        sortSublist.setTitFileNameSf(sftpFileName);
        sortSublist.setTitFilePath(basePath+"/"+directory);
        int count = sortSublistService.addSortSublist(sortSublist);
        if(count >0) {
            map.put("errorCode", "000000");
            map.put("errorMsg", "新增关于我们子列表成功！");
        }else{
            map.put("errorCode", "000001");
            map.put("errorMsg", "新增关于我们子列表失败！");
        }
        return map;
    }

    /**
     * 图片回显示
     * @param req
     * @param response
     */
    @RequestMapping("/downloadSortSublistFile")
    public void downloadSortSublistFile(HttpServletRequest req, HttpServletResponse response){
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
     * 根据sid查询关于我们子列表详细信息
     * @param sortSublist
     * @return
     */
    @RequestMapping("/getSortSublistInfo")
    @ResponseBody
    public Map<String, Object> getSortSublistInfo(SortSublist sortSublist){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer sId = sortSublist.getsId();
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            SortSublist sortSublist1 = sortSublistService.getSortSublistInfo(sId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "根据sid查询关于我们子列表详细信息成功!");
            map.put("sortSublist",sortSublist1);
        }
        return  map;
    }

    /**
     * 通过SID修改关于我们子列表信息
     * @param sortSublist
     * @return
     */
    @RequestMapping(value="/updateSortSublist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSortSublist(@RequestParam(value="codeUrl",required = false) MultipartFile file,SortSublist sortSublist){
        Map<String, Object> map = new HashMap<String, Object>();
        String fileName  = file.getOriginalFilename();//得到真实的文件名
        if(!fileName.equals("")) {
            String lastName = fileName.substring(fileName.lastIndexOf("."));//获得文件的后缀
            String sftpFileName = UUID.randomUUID().toString() + lastName;

            SFTPUtil sftp = new SFTPUtil("sftpUser", "sftpUser123", "47.106.195.175", 22);
            try {
                sftp.login();
                InputStream is = file.getInputStream();//按此可以得到流
                sftp.upload(basePath, directory, sftpFileName, is);
                sftp.logout();
            } catch (IOException | SftpException e) {
                sftp.logout();
                map.put("errorCode", "500");
                map.put("errorMsg", "文件保存失败！");
            } finally {
                sftp.logout();
            }

            sortSublist.setTitFileName(fileName);
            sortSublist.setTitFileNameSf(sftpFileName);
            sortSublist.setTitFilePath(basePath+"/"+directory);
        }

        Integer sId = sortSublist.getsId();
        sortSublist.setUpdateBy("admin");
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            int sortSublistSize = sortSublistService.updateSortSublist(sortSublist);
            if(sortSublistSize >0) {
                map.put("errorCode", "000000");
                map.put("errorMsg", "修改关于我们子列表成功！");
            }else{
                map.put("errorCode", "000001");
                map.put("errorMsg", "修改关于我们子列表失败！");
            }
        }
        return  map;
    }
    /**
     * 通过SID删除关于我们子列表信息
     * @param request
     * @return
     */
    @RequestMapping(value="/delSortSublist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delSortSublist(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer sId =Integer.parseInt(request.getParameter("sId"));
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            sortSublistService.delSortSublist(sId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "删除关于我们子列表成功！");
        }
        return  map;
    }

}
