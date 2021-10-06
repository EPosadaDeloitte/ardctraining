package com.ardctraining.facades.product.impl;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.service.CustomProductLabelService;
import com.ardctraining.facades.product.ArdctrainingProductFacade;
import com.ardctraining.facades.product.data.CustomProductLabelData;
import de.hybris.platform.commercefacades.product.impl.DefaultProductFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultArdctrainingProductFacade extends DefaultProductFacade implements ArdctrainingProductFacade {
    private final static Logger LOG = Logger.getLogger(DefaultArdctrainingProductFacade.class);
    private CustomProductLabelService customProductLabelService;
    private Converter<CustomProductLabelModel, CustomProductLabelData> customProductLabelConverter; // error for being different in bean than java counterpart??

    @Override
    public List<CustomProductLabelData> getCustomLabels(String productCode) {
        try {
            final ProductModel productModel = getProductService().getProductForCode(productCode);
            final UserModel userModel = getUserService().getCurrentUser();
            if (Objects.nonNull(userModel) && userModel instanceof CustomerModel) {
                final CustomerModel customerModel = (CustomerModel) userModel;
                final List<CustomProductLabelModel> labels = getCustomProductLabelService().findByCustomerAndProduct(customerModel,productModel);
                return getCustomProductLabelConverter().convertAll(labels);
            } else {
                LOG.error("unable to find product with code"+productCode);
            }

        } catch (final UnknownIdentifierException | AmbiguousIdentifierException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<CustomProductLabelData> getCustomLabelsByCustomerAndProduct(String customerId, String productCode) {
        final CustomerModel customerModel = getUserService().getUserForUID(customerId,CustomerModel.class);
        final ProductModel productModel = getProductService().getProductForCode(productCode);
        return getCustomProductLabelConverter().convertAll(getCustomProductLabelService().findByCustomerAndProduct(customerModel,productModel));
    }

    // Getters and Setters

    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
    }

    public Converter<CustomProductLabelModel, CustomProductLabelData> getCustomProductLabelConverter() {
        return customProductLabelConverter;
    }

    public void setCustomProductLabelConverter(Converter<CustomProductLabelModel, CustomProductLabelData> customProductLabelConverter) {
        this.customProductLabelConverter = customProductLabelConverter;
    }
}
