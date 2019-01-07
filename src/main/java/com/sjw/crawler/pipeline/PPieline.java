package com.sjw.crawler.pipeline;

import cn.hutool.core.bean.BeanUtil;
import com.sjw.dao.ProductDao;
import com.sjw.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/21.
 */
@Service
public class PPieline implements Pipeline {

    @Autowired
    private ProductDao dao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> all = resultItems.getAll();
        Request request = resultItems.getRequest();
        String url = request.getUrl();
        try {
            String host = new URL(url).getHost().replace("www.", "");
            all.put("host", host);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Object category = request.getExtra("category");
        if (category != null) {
            all.put("category", category);
            ProductEntity productEntity = BeanUtil.mapToBean(all, ProductEntity.class, false);
            dao.saveAndFlush(productEntity);
        }

    }
}
