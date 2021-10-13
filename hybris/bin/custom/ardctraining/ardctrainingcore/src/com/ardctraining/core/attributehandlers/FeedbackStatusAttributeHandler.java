package com.ardctraining.core.attributehandlers;

import com.ardctraining.core.enums.FeedbackStatusEnum;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FeedbackStatusAttributeHandler implements DynamicAttributeHandler<FeedbackStatusEnum, CustomerFeedbackModel> {

    // this is run and done since the status data is not saved in the db

    private ConfigurationService configurationService;

    @Override
    public FeedbackStatusEnum get(CustomerFeedbackModel model) {
        /*
        * When evaluating the feedbackstatus, the following condtions are taken into account to obtain the adequate statusEnum :
        *
        * If the sysadminuser saves a feedback as read afterthe deadline, the status will be READ_PASTDUE
        * If the sysadminuser saves a feedback as read beforethe deadline, the status will be READ
        * If the deadline haspassedand the sysadminuser hasn’t saved the feedback as read, the status will be PASTDUE
        * If the deadline hasn’t passed and the sysadminuser hasn’t saved the feedback as read, the status will be NOT_READ
        * */
        Integer deadlineDays = getConfigurationService().getConfiguration().getInt("feedback.deadline.days");
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime deadlineDate = model.getSubmittedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(deadlineDays);
        if (model.isRead()) {
            if (localDateTime.isAfter(deadlineDate)) {
                return FeedbackStatusEnum.READ_PASTDUE;
            }
            if (localDateTime.isBefore(deadlineDate)) {
                return FeedbackStatusEnum.READ;
            }
        }
        // if feedback has not been read, avoids redundancy from each condition
        if (localDateTime.isAfter(deadlineDate)) {
            return FeedbackStatusEnum.PAST_DUE;
        }

        return FeedbackStatusEnum.NOT_READ;

        /*if (localDateTime.isBefore(deadlineDate)) {
            return FeedbackStatusEnum.NOT_READ;
        }*/
        // consider the yoda time library, or localetime (primary)
        /*if (model.isRead()) {
            return FeedbackStatusEnum.READ;
        }*/
    }

    @Override
    public void set(CustomerFeedbackModel model, FeedbackStatusEnum feedbackStatusEnum) {
        throw new UnsupportedOperationException("Write is not a valid operation for this dynamic attribute");
    }

    // Getters and Setters

    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
