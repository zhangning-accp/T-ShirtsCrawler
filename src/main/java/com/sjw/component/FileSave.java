package com.sjw.component;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONPath;
import com.sjw.dao.ProductDao;
import com.sjw.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/1.
 */
@RestController
@RequestMapping
public class FileSave {
    @Autowired
    private ProductDao dao;

    private Integer defaultPageSize = 10000;

    @RequestMapping(value = "/file/{host}", method = {RequestMethod.GET, RequestMethod.POST})
    public void getFile(HttpServletResponse res, @PathVariable("host") String host, HttpServletRequest request) {
        List<File> pathList = new ArrayList<>();
        //从数据库读取数据
        Pageable page = PageRequest.of(0, defaultPageSize);
        boolean flag = true;
        //当以host 为all的时候表示下载所有host
        while (flag) {
            List<ProductEntity> all = null;
            try {
                if ("_all".equals(host)) {
                    //当category 为all的时候，表示下载所有的category
                    all = dao.findAll(page).getContent();
                } else {
                    all = dao.findByHostLike(host + "%", page);
                }
            } catch (Exception e) {
                continue;
            }
            //文件路径
            String filePath = "/data/" + host.replace("www.", "") + "_" + page.getPageNumber() + ".csv";
            boolean b = writeFile(all, filePath);
            if (b) {
                pathList.add(new File(filePath));
                page = page.next();
            }
            if (all.size() != defaultPageSize) {
                flag = false;
            }
        }
        //文件的压缩
        File zipFile = new File("/data.zip");
        File zip = ZipUtil.zip(zipFile, Charset.defaultCharset(), false, pathList.toArray(new File[0]));
        //文件的下载
        if (zip.exists()) {
            //现在讲这个文件发送给前台
            res.setHeader("content-type", "application/octet-stream");
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=data.zip");
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = res.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(zipFile));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        boolean del = FileUtil.del(zipFile);

        //删除生成的文件
        for (File file : pathList) {
            boolean del1 = FileUtil.del(file);
            if (del1 = false) {
                del = del1;
                break;
            }
        }
        //删除zip 压缩
        System.out.println("delete " + del);

    }

    @RequestMapping("/showAllCategory")
    public List<String> showAll(String host) {
//        System.out.println("123");
        //在这里根据host,显示所有的category
        List<ProductEntity> productEntities = null;
        if (host != null) {
            productEntities = dao.showCategoryByHost(host);
        } else {
            productEntities = dao.showAllCategoryst();
        }
        List<String> eval = (List<String>) JSONPath.eval(productEntities, "$..category");
        return eval;
    }

    /**
     * 显示所有的host
     *
     * @return 返回host
     */
    @RequestMapping("/showAllHost")
    public List<String> showAllHost() {
        //在这里显示所有的host
        List<ProductEntity> productEntities = dao.showAllHost();
        List<String> eval = (List<String>) JSONPath.eval(productEntities, "$..host");
        return eval;
    }

    public boolean writeFile(List<ProductEntity> list, String fileUrl) {
        File file = new File(fileUrl);
        CsvWriter writer = CsvUtil.getWriter(file, Charset.defaultCharset());
        String[] strings = {"serial_number", "id", "status", "code", "sku", "name", "short_description", "description", "attributes", "head_title", "meta_description", "image", "price", "quantity", "option_groups", "product_groups", "categorie_names", "category_descriptions", "category_short_descriptions", "category_meta_descriptions", "category_h1_titles", "category_head_titles"};
        ArrayList<String[]> str = new ArrayList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                str.add(strings);
            }
            str.add(list.get(i).toArrayStr());
        }
        writer.write(str);
        writer.close();
        return file.exists();

    }
}
