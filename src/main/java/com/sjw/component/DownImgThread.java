package com.sjw.component;

import cn.hutool.core.io.FileUtil;
import com.sjw.entity.ProductEntity;
import com.sjw.tool.OkhttpUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/12/26.
 */
public class DownImgThread implements Callable<ProductEntity> {
    private static Logger logger = LoggerFactory.getLogger(DownImgThread.class.getName());
    private ProductEntity productEntity;

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }


    public Boolean wirteFile(byte[] bytes) throws MalformedURLException {
        String pictureUrl = productEntity.getPictureUrl();
        String path = new URL(pictureUrl).getPath();
        File file = FileUtil.writeBytes(bytes, "\\imageData" + path.replace("/", "\\"));
        if (file.exists()) {
            productEntity.setImagePath(file.getPath());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public ProductEntity call() throws Exception {
        String pictureUrl = productEntity.getPictureUrl();
        Request build = new Request.Builder().url(pictureUrl)
                .addHeader("host", "d1w8c6s6gmwlek.cloudfront.net")
                .addHeader("if-none-match", "2daec05f066459443b6d015e037796d8")
                .addHeader("User-Agent", " Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36")
                .build();
        Response execute = null;
        try {
            execute = OkhttpUtils.execute(build);
            boolean successful = execute.isSuccessful();
            if (successful) {
                byte[] bytes = execute.body().bytes();
                Boolean aBoolean = wirteFile(bytes);
                if (aBoolean) {
                    logger.info("The picture[{}] save Success! ", productEntity.getImagePath());
                    return productEntity;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
