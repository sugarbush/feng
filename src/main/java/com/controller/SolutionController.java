package com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.pojo.Solution;
import com.service.SolutionService;
import com.util.ResponseObj;
import com.util.SFTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
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
public class SolutionController {

    @Autowired
    private SolutionService solutionService;
    ResponseObj responseObj;

    String basePath = "/app/sftpUpload";
    String directory ="jr_system";

    /**
     * 根据查询条件查询解决方案列表_分页
     * @param solution
     * @return
     */
    @RequestMapping("/getSolutionAllList")
    @ResponseBody
    public PageInfo getSolutionAllList(Solution solution,HttpServletRequest request){
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
        solution.setTitle(title);
        solution.setSubtitle(subTitle);
        solution.setPubDate(pubDate);
        List<Solution> list =  solutionService.getSolutionAllList(solution);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }

    /**
     * 根据查询条件查询解决方案列表
     * @param solution
     * @return
     */
    @RequestMapping("/querySolutionAllList")
    @ResponseBody
    public PageInfo querySolutionAllList(Solution solution,HttpServletRequest request){

        String title = request.getParameter("title");
        String subTitle = request.getParameter("subTitle");
        String pubDate = request.getParameter("pubDate");

        //调用service方法
        solution.setTitle(title);
        solution.setSubtitle(subTitle);
        solution.setPubDate(pubDate);
        List<Solution> list =  solutionService.getSolutionAllList(solution);
        PageInfo pageInfo = new PageInfo(list);  //将sql查询结果list传递过去
        return pageInfo;
    }


    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadHeadPic" , method = RequestMethod.POST ,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map uploadHeadPic(@RequestParam() MultipartFile file, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        //在这里面文件存储的方案一般是：收到文件→获取文件名→在本地存储目录建立防重名文件→写入文件→返回成功信息
        //如果上面的步骤中在结束前任意一步失败，那就直接失败了。
        System.out.println(   request.getSession().getServletContext().getRealPath("/") );
        FileOutputStream out=null;
        if (null == file || file.equals("")) {
            model.put("errorCode","400");
            model.put("errorMsg","文件不能为空！");
        }else{
            try {

                ///---------------
                SFTPUtil sftp = new SFTPUtil("sftpUser", "sftpUser123", "47.106.195.175", 22);
                sftp.login();
                // File file = new File("D:\\customer\\47_106_195_175\\ANXIN\\CODE_D\\mapDemo\\img\\icon1.png");
//                InputStream is = new FileInputStream(file);

                InputStream  is = file.getInputStream();
                sftp.upload("/app/sftpUpload","jr_system", "test_sftp.jpg", is);
                sftp.logout();

                model.put("errorCode","200");
                model.put("errorMsg","文件上传成功！");
            } catch (IOException | SftpException e) {
                System.out.println("上传失败");
                model.put("errorCode","500");
                model.put("errorMsg","文件保存失败！");
            }finally {
                // 完毕，关闭所有链接
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("关闭流失败");
                }
            }

//            model.put("errorCode","200");
//            model.put("errorMsg","文件上传成功！");
//            //这里以用户ID作为文件夹
//           // int uid = (Integer) request.getSession().getAttribute("userid");
//
//            int uid = 123;
//            //创建一个文件夹，网上代码很多
//            String url = new FileUtil().createImageDir( String.valueOf(uid));
//            try {
//                //获得二进制流并输出
//                byte[] f = file.getBytes();
//                System.out.println(file.getOriginalFilename());
//                out = new FileOutputStream(url+file.getOriginalFilename());
//                out.write(f);
//
//            } catch (IOException e) {
//                System.out.println("上传失败");
//                model.put("errorCode","500");
//                model.put("errorMsg","文件保存失败！");
//            }finally {
//                // 完毕，关闭所有链接
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    System.out.println("关闭流失败");
//                }
//            }
        }
        return   model;
    }

    /**
     * 新增解决方案
     * @param solution
     * @return
     */
    @RequestMapping(value = "/addSolution" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSolution(@RequestParam(value="codeUrl",required = false) MultipartFile file,Solution solution) {
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
        //------保存解决方案相关信息
        solution.setCreateBy("admin");
        solution.setUpdateBy("admin");
        solution.setTitFileName(fileName);
        solution.setTitFileNameSf(sftpFileName);
        solution.setTitFilePath(basePath+"/"+directory);
        int count = solutionService.addSolution(solution);
        if(count >0) {
            map.put("errorCode", "000000");
            map.put("errorMsg", "新增解决方案成功！");
        }else{
            map.put("errorCode", "000001");
            map.put("errorMsg", "新增解决方案失败！");
        }
        return map;
    }

    /**
     * 图片回显示
     * @param req
     * @param response
     */
    @RequestMapping("/downloadFile")
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
     * 根据sid查询解决方案详细信息
     * @param solution
     * @return
     */
    @RequestMapping("/getSolutionInfo")
    @ResponseBody
    public Map<String, Object> getSolutionInfo(Solution solution){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer sId = solution.getSId();
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            Solution solution1 = solutionService.getSolutionInfo(sId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "根据sid查询解决方案详细信息成功!");
            map.put("solution",solution1);
        }
        return  map;
    }

    /**
     * 通过SID修改解决方案信息
     * @param solution
     * @return
     */
    @RequestMapping(value="/updateSolution", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSolution(@RequestParam(value="codeUrl",required = false) MultipartFile file,Solution solution){
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

        Integer sId = solution.getSId();
        solution.setUpdateBy("admin");
        solution.setTitFileName(fileName);
        solution.setTitFileNameSf(sftpFileName);
        solution.setTitFilePath(basePath+"/"+directory);
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            int solutionSize = solutionService.updateSolution(solution);
            if(solutionSize >0) {
                map.put("errorCode", "000000");
                map.put("errorMsg", "修改解决方案成功！");
            }else{
                map.put("errorCode", "000001");
                map.put("errorMsg", "修改解决方案失败！");
            }
        }
        return  map;
    }
    /**
     * 通过SID删除解决方案信息
     * @param solution
     * @return
     */
    @RequestMapping(value="/delSolution", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delSolution(Solution solution,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer sId =Integer.parseInt(request.getParameter("sId"));
        if(sId.equals("")){
            map.put("errorCode", "000001");
            map.put("errorMsg", "缺少唯一编码，请确认！");
        }else{
            solutionService.delSolution(sId);
            map.put("errorCode", "000000");
            map.put("errorMsg", "删除解决方案成功！");
        }
        return  map;
    }

}
