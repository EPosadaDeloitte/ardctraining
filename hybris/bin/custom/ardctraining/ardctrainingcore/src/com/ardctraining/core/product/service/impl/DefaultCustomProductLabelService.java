package com.ardctraining.core.product.service.impl;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.dao.CustomProductLabelDao;
import com.ardctraining.core.product.dao.impl.DefaultCustomProductLabelDao;
import com.ardctraining.core.product.service.CustomProductLabelService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.servicelayer.time.TimeService;

import java.util.Date;
import java.util.List;

public class DefaultCustomProductLabelService implements CustomProductLabelService {

    private CustomProductLabelDao customProductLabelDao;
    private TimeService timeService;

    @Override
    public List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customerModel, ProductModel productModel) {
        ServicesUtil.validateParameterNotNull(customerModel,"customer cannot be null");
        ServicesUtil.validateParameterNotNull(productModel,"product cannot be null");
        return getCustomProductLabelDao().findByCustomerAndProduct(customerModel, productModel);
    }

    @Override
    public List<CustomProductLabelModel> findExpired() {
        final Date now = getTimeService().getCurrentTime();
        return getCustomProductLabelDao().findExpired(now);
    }

    // Getters and Setters

    public CustomProductLabelDao getCustomProductLabelDao() {
        return customProductLabelDao;
    }

    public void setCustomProductLabelDao(CustomProductLabelDao customProductLabelDao) {
        this.customProductLabelDao = customProductLabelDao;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
