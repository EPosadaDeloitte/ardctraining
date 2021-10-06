package com.ardctraining.core.product.service;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.dao.impl.DefaultCustomProductLabelDao;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomProductLabelService {

    List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customerModel, ProductModel productModel);
    List<CustomProductLabelModel> findExpired();
}
