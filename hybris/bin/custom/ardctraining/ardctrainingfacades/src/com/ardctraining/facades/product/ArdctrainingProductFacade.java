package com.ardctraining.facades.product;

import com.ardctraining.facades.product.data.CustomProductLabelData;

import java.util.List;

public interface ArdctrainingProductFacade {
    List<CustomProductLabelData> getCustomLabels(final String product);
    List<CustomProductLabelData> getCustomLabelsByCustomerAndProduct(String customerId, String productCode);
}
