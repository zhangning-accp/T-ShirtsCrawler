package com.sjw.dao;

import com.sjw.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
public interface ProductDao extends JpaRepository<ProductEntity, String> {

    @Query(value = "from ProductEntity  group by host order by host")
    List<ProductEntity> showAllHost();

    @Query(value = " from ProductEntity  GROUP BY category ORDER BY category")
    List<ProductEntity> showAllCategoryst();

    @Query(value = "select category from product where host=:host GROUP BY category ORDER BY category", nativeQuery = true)
    List<ProductEntity> showCategoryByHost(@PathVariable("host") String host);

    List<ProductEntity> findByHostLike(@PathVariable("host") String host, Pageable page);


}
