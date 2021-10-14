package com.ardctraining.core.feedback.service.impl;

import com.ardctraining.core.feedback.dao.FeedbackDao;
import com.ardctraining.core.feedback.service.FeedbackService;
import com.ardctraining.core.model.CustomProductLabelCleanupEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedbackEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultFeedbackService implements FeedbackService {

    private ModelService modelService;
    private FeedbackDao feedbackDao;

    private BusinessProcessService businessProcessService;
    private BaseSiteService baseSiteService;
    private TimeService timeService;

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

        // send email
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm:ss.S");
        final Date now = getTimeService().getCurrentTime();

        final CustomerFeedbackEmailProcessModel emailProcessModel = getBusinessProcessService().createProcess(
                new StringBuilder().append("customerFeedbackEmailProcess").append("-").append(dateFormat.format(now)).toString(),
                "customerFeedbackEmailProcess"
        );
        //LanguageModel lang = new LanguageModel();
        //lang.setIsocode("en");

        //emailProcessModel.setLanguage(customProductLabelCleanupCronjobModel.getSessionLanguage());
        //emailProcessModel.setLanguage(lang); // hardcoded
        emailProcessModel.setLanguage(getBaseSiteService().getCurrentBaseSite().getDefaultLanguage());
        emailProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
        //emailProcessModel.setSite(getBaseSiteService().getBaseSiteForUID("electronics"));
        // emailProcessModel.setCustomerFeedback();
        emailProcessModel.setSubject(customerFeedbackModel.getSubject());
        emailProcessModel.setMessage(customerFeedbackModel.getMessage());

        emailProcessModel.setUser((UserModel) customerModel);

        modelService.save(emailProcessModel);
        getBusinessProcessService().startProcess(emailProcessModel); // actually sends the email, once object is ready
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

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    public void setFeedbackDao(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }
}
