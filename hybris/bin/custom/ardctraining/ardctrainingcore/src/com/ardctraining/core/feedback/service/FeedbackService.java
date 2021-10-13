package com.ardctraining.core.feedback.service;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface FeedbackService {

    List<CustomerFeedbackModel> findByCustomerAndNotreadOrPastdue(final CustomerModel customerModel);
    void save(final CustomerModel customerModel,final CustomerFeedbackModel customerFeedbackModel);

}
