package com.ardctraining.core.feedback.service.impl;

import com.ardctraining.core.feedback.dao.FeedbackDao;
import com.ardctraining.core.feedback.service.FeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.Date;
import java.util.List;

public class DefaultFeedbackService implements FeedbackService {

    private ModelService modelService;
    private FeedbackDao feedbackDao;

    @Override
    public List<CustomerFeedbackModel> findByCustomerAndNotreadOrPastdue(CustomerModel customerModel) {
        ServicesUtil.validateParameterNotNull(customerModel,"customer cannot be null");
        return getFeedbackDao().findByCustomerUnreadFeedbacks(customerModel);
    }

    @Override
    public void save(CustomerModel customerModel,CustomerFeedbackModel customerFeedbackModel) {
        //modelService.save(customerFeedbackModel);
        // logic validation
        customerFeedbackModel.setCustomer(customerModel);
        // validate parameters
        ServicesUtil.validateParameterNotNull(customerModel,"customer cannot be null");
        //ServicesUtil.validateParameterNotNull(customerFeedbackModel.getStatus(),"status cannot be empty????");
        ServicesUtil.validateParameterNotNull(customerFeedbackModel.getSubject(),"subject cannot be empty");
        ServicesUtil.validateParameterNotNull(customerFeedbackModel.getMessage(),"message cannot be empty");
        customerFeedbackModel.setRead(false);
        customerFeedbackModel.setSubmittedDate(new Date());
        getModelService().save(customerFeedbackModel);
    }

    // Getters and Setters

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public FeedbackDao getFeedbackDao() {
        return feedbackDao;
    }

    public void setFeedbackDao(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }
}
