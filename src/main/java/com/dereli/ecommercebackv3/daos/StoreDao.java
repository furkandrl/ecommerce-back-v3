package com.dereli.ecommercebackv3.daos;

import com.dereli.ecommercebackv3.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDao extends JpaRepository<Store, Long> {

    Store findStoreByCode(String code);

}
