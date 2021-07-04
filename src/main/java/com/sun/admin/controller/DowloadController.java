package com.sun.admin.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@Controller
public class DowloadController {

    /**
     * 1. 将文件以流的形式一次性读取到内存，通过响应输出流输出到前端
     * @param path
     * @param response
     */
    @ResponseBody
    @RequestMapping("/download/{path}")
    public String dowload(@PathVariable("path") String path, HttpServletResponse response){
        FileInputStream fileInputStream = null;
        InputStream fis = null;
        try {
            path="D:\\"+path;
            log.info(path );
            File file = new File(path);

            if (!file.exists()){
                return "文件未找到";
            }
            log.info(file.getPath());
            //获取文件名
            String name = file.getName();
            String ext = name.substring(name.indexOf(".") + 1).toLowerCase();

            fileInputStream = new FileInputStream(file);
            fis = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[fis.available()];
            // 将文件写入输入流
            fis.read(bytes);
            fis.close();
            // 清空response
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            response.addHeader("Content-Length",""+file.length() );

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return "成功";
    }
}
