package com.sjw.crawler.lancher;

import cn.hutool.core.date.DateUtil;
import com.sjw.component.SendMail;
import com.sjw.crawler.pageprocesser.PPageProcesser;
import com.sjw.crawler.pipeline.PPieline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2018/12/21.
 * 启动抓取 列表页的爬虫， 暂未完成，
 * 不能抓取全部列表。
 * 不能进行分页
 */
@Component
public class PLancher {
    @Autowired
    private PPieline pPieline;
    private List<String> list = new ArrayList<String>();

    @Autowired
    private SendMail sendMail;

    @Autowired
    private ImgLancher imgLancher;

    {
        list.add("https://www.millionand1tees.com".trim());
        list.add("https://www.lovemytowntees.com".trim());
        list.add("https://www.limitedtees.co".trim());
        list.add("https://www.jobbertshirts.com".trim());
        list.add("https://www.hockeyfantshirts.com".trim());
        list.add("https://www.hiphopshirt.com".trim());
        list.add("https://www.flatlandtees.com".trim());
        list.add("https://www.drinkshirts.com".trim());
        list.add("https://www.classicgametees.com".trim());
        list.add("https://www.bookwormtees.com".trim());
        list.add("https://www.basketballfantshirts.com".trim());
        list.add("https://www.basketballcaricaturetshirts.com".trim());
        list.add("https://www.baseballfantshirts.com".trim());
    }

    public void stratCrawler() throws ExecutionException, InterruptedException {
        sendMail.SendEmail("Cralwer is starting," + DateUtil.date(), "Cralwer", "zhangning_holley@126.com");
        Request[] request = getRequest();
        Spider.create(new PPageProcesser())
                .addPipeline(pPieline)
                .addRequest(request)
                .thread(10)
                .addUrl()
                .run();
        sendMail.SendEmail("Cralwer is end," + DateUtil.date(), "Cralwer", "zhangning_holley@126.com");
        imgLancher.startDownload();
    }

    public Request[] getRequest() {
        Request[] requests = new Request[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            Request request = new Request();
            request.setUrl(str);
            request.putExtra("host", str);
            requests[i] = request;
        }
        return requests;
    }
}
