package com.ardctraining.core.feedback.dao;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface FeedbackDao {

    List<CustomerFeedbackModel> findByCustomerUnreadFeedbacks(final CustomerModel customerModel);
}
