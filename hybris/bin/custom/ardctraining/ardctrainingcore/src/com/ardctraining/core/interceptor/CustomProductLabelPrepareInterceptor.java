package com.ardctraining.core.interceptor;

import com.ardctraining.core.enums.CustomLabelTypeEnum;
import com.ardctraining.core.model.CustomProductLabelModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import java.util.Objects;

public class CustomProductLabelPrepareInterceptor implements PrepareInterceptor<CustomProductLabelModel> {

    @Override
    public void onPrepare(CustomProductLabelModel customProductLabelModel, InterceptorContext interceptorContext) throws IllegalArgumentException {
        if (Objects.isNull(customProductLabelModel.getLabelType())){
            customProductLabelModel.setLabelType(CustomLabelTypeEnum.PRIMARY);
        }
        if (Objects.nonNull(customProductLabelModel.getCustomer()) && customProductLabelModel.getCustomer().getCustomerID() == null){
            //throw InterceptorException();
            throw new IllegalArgumentException("Unable to save label");
        }
    }
}
