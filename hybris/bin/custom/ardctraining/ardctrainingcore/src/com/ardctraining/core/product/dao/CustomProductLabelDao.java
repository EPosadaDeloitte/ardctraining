package com.ardctraining.core.product.dao;

import com.ardctraining.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;

public interface CustomProductLabelDao {
    List<CustomProductLabelModel> findByCustomerAndProduct(final CustomerModel customer, final ProductModel product);
    List<CustomProductLabelModel> findExpired(Date now);
    List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(final CustomerModel customer,final ProductModel product);
}
