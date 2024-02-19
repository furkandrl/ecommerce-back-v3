package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelDao extends JpaRepository<Item, Long> {
}
