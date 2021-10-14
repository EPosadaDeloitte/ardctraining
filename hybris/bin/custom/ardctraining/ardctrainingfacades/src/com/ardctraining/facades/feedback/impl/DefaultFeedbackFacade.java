package com.ardctraining.facades.feedback.impl;

import com.ardctraining.core.feedback.service.FeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.FeedbackFacade;
import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultFeedbackFacade extends DefaultCustomerFacade implements FeedbackFacade {
    private final static Logger LOG = Logger.getLogger(DefaultFeedbackFacade.class);
    private FeedbackService feedbackService;
    private Converter<CustomerFeedbackModel, CustomerFeedbackData> customerFeedbackDataConverter;


    @Override
    public List<CustomerFeedbackData> getFeedbackForms() {
        try {
            final UserModel userModel = getUserService().getCurrentUser();
            if (Objects.nonNull(userModel) && userModel instanceof CustomerModel) {
                final CustomerModel customerModel = (CustomerModel) userModel;
                final List<CustomerFeedbackModel> customerFeedbackModels = getFeedbackService().findByCustomerAndNotreadOrPastdue(customerModel);
                return getCustomerFeedbackDataConverter().convertAll(customerFeedbackModels);
            } else {
                LOG.error("there was an error upon getting customer feedbacks");
            }
        } catch (UnknownIdentifierException | AmbiguousIdentifierException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean saveForm(CustomerFeedbackData customerFeedbackData) {
        try {
            final UserModel userModel = getUserService().getCurrentUser();
            if (Objects.nonNull(userModel) && userModel instanceof CustomerModel) {
                final CustomerModel customerModel = (CustomerModel) userModel;
                final CustomerFeedbackModel customerFeedbackModel = new CustomerFeedbackModel();
                customerFeedbackModel.setSubject(customerFeedbackData.getSubject());
                customerFeedbackModel.setCreationtime(customerFeedbackData.getCreationtime());
                customerFeedbackModel.setMessage(customerFeedbackData.getMessage());
                getFeedbackService().save(customerModel,customerFeedbackModel);

                LOG.info("form was saved successfully");
                return true;
            }
        } catch (UnknownIdentifierException | AmbiguousIdentifierException e) {
            e.printStackTrace();
        }
        LOG.error("there was an error saving the form");
        return false;
    }

    // Getters and Setters

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    public Converter<CustomerFeedbackModel, CustomerFeedbackData> getCustomerFeedbackDataConverter() {
        return customerFeedbackDataConverter;
    }

    public void setCustomerFeedbackDataConverter(Converter<CustomerFeedbackModel, CustomerFeedbackData> customerFeedbackDataConverter) {
        this.customerFeedbackDataConverter = customerFeedbackDataConverter;
    }
}
