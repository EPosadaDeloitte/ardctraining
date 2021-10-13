package com.ardctraining.facades.populators;

import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Date;
import java.util.Locale;

public class FeedbackPopulator implements Populator<CustomerFeedbackModel, CustomerFeedbackData> {

    @Override
    public void populate(CustomerFeedbackModel customerFeedbackModel, CustomerFeedbackData customerFeedbackData) throws ConversionException {
        customerFeedbackData.setCreationtime(customerFeedbackModel.getSubmittedDate()); // submitted date (perhaps it could be using customerFeedbackData.setCreationtime(new Date()))
        customerFeedbackData.setSubject(customerFeedbackModel.getSubject());
        customerFeedbackData.setStatus(customerFeedbackModel.getStatus());
        customerFeedbackData.setMessage(customerFeedbackModel.getMessage());
    }
}
