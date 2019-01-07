package com.sjw.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2019/1/2.
 */
@Entity
@Table(name = "product", schema = "", catalog = "product")
@IdClass(ProductEntityPK.class)
public class ProductEntity {
    private String pictureUrl;
    private String imagePath;
    private String category;
    private String url;
    private int sku;
    private String title;
    private String host;
    private String productDetails;

    @Basic
    @Column(name = "picture_url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Basic
    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Id
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Id
    @Column(name = "sku")
    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @Column(name = "host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Basic
    @Column(name = "product_details")
    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntity that = (ProductEntity) o;

        if (sku != that.sku) {
            return false;
        }
        if (pictureUrl != null ? !pictureUrl.equals(that.pictureUrl) : that.pictureUrl != null) {
            return false;
        }
        if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (host != null ? !host.equals(that.host) : that.host != null) {
            return false;
        }
        if (productDetails != null ? !productDetails.equals(that.productDetails) : that.productDetails != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = pictureUrl != null ? pictureUrl.hashCode() : 0;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + sku;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (productDetails != null ? productDetails.hashCode() : 0);
        return result;
    }

    public String[] toArrayStr() {
        String[] str = {"","","1",String.valueOf(sku),"",title,title,title,productDetails,title,title,imagePath,"","","","",category,category,category,category,category,category};
        return str;
    }
}
