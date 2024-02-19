package com.dereli.ecommercebackv3.services;

import com.dereli.ecommercebackv3.dtos.requests.StoreRequest;
import com.dereli.ecommercebackv3.dtos.responses.StoreResponse;
import com.dereli.ecommercebackv3.models.Store;

public interface StoreService {

    StoreResponse getStore();

    Store getStoreModel();

    void updateStore(StoreRequest storeRequest);

}
