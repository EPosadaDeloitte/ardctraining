package com.ardctraining.facades.process.email.context;


import com.ardctraining.core.model.CustomerFeedbackEmailProcessModel;
import com.ardctraining.facades.constants.ArdctrainingFacadesConstants;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.log4j.Logger;

public class FeedbackEmailContext extends AbstractEmailContext<CustomerFeedbackEmailProcessModel> {

    private ConfigurationService configurationService;
    private Logger LOG = Logger.getLogger(FeedbackEmailContext.class);

    private String subject;
    private String message;

    @Override
    public void init (CustomerFeedbackEmailProcessModel customerFeedbackEmailProcessModel, EmailPageModel emailPageModel) {
        super.init(customerFeedbackEmailProcessModel,emailPageModel);
        LOG.info("Entering init method for "+this.getClass().getSimpleName());
        setSubject(customerFeedbackEmailProcessModel.getSubject());
        setMessage(customerFeedbackEmailProcessModel.getMessage());
        // optional, set customer

        //getCustomerFeedbackData();
        //customerFeedbackEmailProcessModel
    }

    @Override
    public String getToEmail() {
        return getConfigurationService().getConfiguration().getString(ArdctrainingFacadesConstants.PRODUCT_LABELS_CRONJOB_RECIPIENT_EMAIL);
    }

    @Override
    public String getToDisplayName() {
        return getConfigurationService().getConfiguration().getString(ArdctrainingFacadesConstants.PRODUCT_LABELS_CRONJOB_RECIPIENT_DISPLAYNAME);
    }

    @Override
    protected BaseSiteModel getSite(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return (CustomerModel) businessProcessModel.getUser();
    }

    @Override
    protected LanguageModel getEmailLanguage(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }

    // Getters and Setters

    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    @Override
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
