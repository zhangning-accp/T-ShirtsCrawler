package com.sjw.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/2.
 */
public class ProductEntityPK implements Serializable {
    private String category;
    private int sku;
    private String host;

    @Column(name = "category")
    @Id
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "sku")
    @Id
    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    @Column(name = "host")
    @Id
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntityPK that = (ProductEntityPK) o;

        if (sku != that.sku) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        if (host != null ? !host.equals(that.host) : that.host != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + sku;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        return result;
    }
}
