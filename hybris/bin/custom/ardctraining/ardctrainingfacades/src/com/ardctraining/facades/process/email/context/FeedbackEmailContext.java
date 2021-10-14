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

    @Override
    public void init (CustomerFeedbackEmailProcessModel customerFeedbackEmailProcessModel, EmailPageModel emailPageModel) {
        super.init(customerFeedbackEmailProcessModel,emailPageModel);
        LOG.info("Entering init method for "+this.getClass().getSimpleName());
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
        return null;
    }

    @Override
    protected CustomerModel getCustomer(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return null;
    }

    @Override
    protected LanguageModel getEmailLanguage(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return null;
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
}
