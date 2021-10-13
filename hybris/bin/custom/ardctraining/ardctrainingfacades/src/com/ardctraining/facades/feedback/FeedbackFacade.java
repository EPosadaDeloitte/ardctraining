package com.ardctraining.facades.feedback;

import com.ardctraining.facades.feedback.data.CustomerFeedbackData;

import java.util.List;

public interface FeedbackFacade {

    // Methods for getting data and saving forms
    List<CustomerFeedbackData> getFeedbackForms();
    boolean saveForm(CustomerFeedbackData customerFeedbackData);

}
