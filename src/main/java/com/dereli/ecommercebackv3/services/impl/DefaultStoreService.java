package com.dereli.ecommercebackv3.services.impl;

import com.dereli.ecommercebackv3.constants.DereliCoreConstants;
import com.dereli.ecommercebackv3.daos.StoreDao;
import com.dereli.ecommercebackv3.dtos.requests.StoreRequest;
import com.dereli.ecommercebackv3.dtos.responses.StoreResponse;
import com.dereli.ecommercebackv3.models.Store;
import com.dereli.ecommercebackv3.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultStoreService implements StoreService {

    @Resource
    private StoreDao storeDao;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public StoreResponse getStore() {
        Store store = storeDao.findStoreByCode(DereliCoreConstants.DERELI_STORE_CODE);
        return modelMapper.map(store, StoreResponse.class);
    }

    @Override
    public Store getStoreModel() {
        Store store = storeDao.findStoreByCode(DereliCoreConstants.DERELI_STORE_CODE);
        if (store == null) {
            store = new Store();
            store.setCode(DereliCoreConstants.DERELI_STORE_CODE);
            store.setName(DereliCoreConstants.DERELI_STORE_NAME);
            store.setLogoUrl("/logo");
            store.setShippingCost(10D);
            store.setFreeShippingThreshold(100D);
            storeDao.save(store);
            return storeDao.findStoreByCode(DereliCoreConstants.DERELI_STORE_CODE);
        }
        return store;
    }

    @Override
    public void updateStore(StoreRequest storeRequest) {
        Store store = modelMapper.map(storeRequest, Store.class);
        storeDao.save(store);
    }
}
