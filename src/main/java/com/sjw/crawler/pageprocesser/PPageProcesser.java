package com.sjw.crawler.pageprocesser;

import com.sjw.crawler.pipeline.PPieline;
import com.sjw.tool.UrlUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
public class PPageProcesser implements PageProcessor {
    Site site = Site.me().setSleepTime(5000)
            .setRetryTimes(10)
            .setCycleRetryTimes(10)
            .setRetrySleepTime(5000)
            .setTimeOut(5000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");

    @Override
    public void process(Page page) {
        Selectable url = page.getUrl();
        Object host = page.getRequest().getExtra("host");
        if (host != null && url.get().equals(host.toString())) {
            List<Selectable> nodes = page.getHtml().xpath("//div[@id='sidebar']//li/a").nodes();
            for (int i = 1; i < nodes.size(); i++) {
                Selectable selectable = nodes.get(i);
                String href = selectable.links().get();
                page.addTargetRequest(href);
            }
            if (page.getHtml().xpath("//li[@class='sidebar_more active']").get() != null) {
                page.addTargetRequest(url.get() + "/js/sidebarCategories.php");
            }
        } else if (url.regex("sidebarCategories.php").match()) {
            List<String> all = page.getHtml().links().all();
            page.addTargetRequests(all);
        } else if (page.getUrl().regex("/view").match()) {
            //产品名称
            String title = page.getHtml().xpath("//h1/text()").get();
            page.putField("title", title);
            //产品页链接
            String product_url = page.getUrl().get();
            page.putField("url", product_url);
            //图片的路径
            String picture_url = page.getHtml().xpath("//img[@id='firstLoad']/@src").get();
            page.putField("pictureUrl", picture_url);
            //sku
            String sku = page.getHtml().xpath("//section/div/@id").get();
            page.putField("sku", sku);
            //product_details
            String all = page.getHtml().xpath("//div[@class='leftCorner']/div[@class='productDetails']/ul").get();
            page.putField("productDetails", all.replace("\n","").replace("\r","").replace("\"","'"));
        } else {
            List<String> all = page.getHtml().xpath("//div/a[@class]/@href").all();
            for (String product_url : all) {
                String[] paths = UrlUtil.getPaths(url.get());
                Request request = new Request();
                request.putExtra("category", paths[2]);
                try {
                    request.setUrl("https://"+new URL(url.get()).getHost() + product_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                page.addTargetRequest(request);
            }
            String nextUrl = page.getHtml().xpath("//li[@class='next']/a/@href").get();
            if (nextUrl != null) {
                page.addTargetRequest(nextUrl);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PPageProcesser())
                .addPipeline(new PPieline())
                .addUrl("https://www.basketballfantshirts.com")
                .start();
    }

}
