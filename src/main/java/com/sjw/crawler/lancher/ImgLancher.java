package com.sjw.crawler.lancher;

import cn.hutool.core.date.DateUtil;
import com.sjw.component.DownImgThread;
import com.sjw.component.SendMail;
import com.sjw.dao.ProductDao;
import com.sjw.entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/12/22.
 * 这边主要用来下载图片，图片的话
 */
@Component
public class ImgLancher {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(50, 50, 0,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
    Integer pageDefaultSize = 0;
    @Autowired
    private ProductDao dao;
    @Autowired
    private SendMail sendMail;

    public void startDownload() throws ExecutionException, InterruptedException {
        sendMail.SendEmail("ImgDown is starting," + DateUtil.date(), "ImgDown", "zhangning_holley@126.com");
        Pageable page = PageRequest.of(0, 70);
        page = page.first();
        Page<ProductEntity> all = dao.findAll(page);
        pageDefaultSize = all.getSize();
        Boolean flag = true;
        while (flag) {
            Iterator<ProductEntity> iterator = all.iterator();
            ArrayList<Future<ProductEntity>> futures = new ArrayList<>();
            while (iterator.hasNext()) {
                ProductEntity next = iterator.next();
                String imagePath = next.getImagePath();
                if (imagePath == null || !new File(imagePath).exists()) {
                    //当这个文件不存在的时候则需要进行文件下载，
                    DownImgThread downImgThread = new DownImgThread();
                    downImgThread.setProductEntity(next);
                    Future<ProductEntity> submit = poolExecutor.submit(downImgThread);
                    futures.add(submit);
                }
            }
            for (int i = 0; i < futures.size(); i++) {
                Future<ProductEntity> productEntityFuture = futures.get(i);
                ProductEntity productEntity = productEntityFuture.get();
                if (productEntity != null) {
                    String imagePath = productEntity.getImagePath();
                    if (imagePath != null && new File(imagePath).exists()) {
                        log.info("this file[{}] is exist", productEntity.getImagePath());
                        try {
                            dao.save(productEntity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            page = page.next();
            try {
                all = dao.findAll(page);
            } catch (Exception e) {
                e.printStackTrace();
                all = dao.findAll(page);
            }
            if (all.getSize() != pageDefaultSize) {
                flag = false;
            }
            sendMail.SendEmail("ImgDown is starting," + DateUtil.date(), "ImgDown", "zhangning_holley@126.com");
        }
    }


}
