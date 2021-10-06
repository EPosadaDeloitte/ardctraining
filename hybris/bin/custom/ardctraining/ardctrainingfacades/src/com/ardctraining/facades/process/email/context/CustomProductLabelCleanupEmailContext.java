package com.ardctraining.facades.process.email.context;

import com.ardctraining.core.jalo.CustomProductLabel;
import com.ardctraining.core.model.CustomProductLabelCleanupEmailProcessModel;
import com.ardctraining.facades.constants.ArdctrainingFacadesConstants;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomProductLabelCleanupEmailContext extends AbstractEmailContext<CustomProductLabelCleanupEmailProcessModel> {

    private ConfigurationService configurationService;

    private Integer size;
    private Set<Map<String,String>> labels;
    private Logger LOG = Logger.getLogger(CustomProductLabelCleanupEmailContext.class);

    @Override
    public void init (CustomProductLabelCleanupEmailProcessModel customProductLabelCleanupEmailProcessModel, EmailPageModel emailPageModel) {
        super.init(customProductLabelCleanupEmailProcessModel,emailPageModel);
        LOG.info("Entering init method for "+this.getClass().getSimpleName());
        Set<String> customLabels = customProductLabelCleanupEmailProcessModel.getCustomLabels();
        setSize(customLabels.size());
        setLabels(customLabels);
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
    protected BaseSiteModel getSite(CustomProductLabelCleanupEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(CustomProductLabelCleanupEmailProcessModel businessProcessModel) {
        return (CustomerModel) businessProcessModel.getUser();
    }

    @Override
    protected LanguageModel getEmailLanguage(CustomProductLabelCleanupEmailProcessModel businessProcessModel) {
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Map<String, String>> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        final Set<Map<String,String>> customLabels = new HashSet<>();
        labels.forEach((String s) -> {
            final Map<String,String> label = new HashMap<>();
            final String[] parts = s.split("\\|");
            if (parts.length > 0) {
                label.put("customer", StringUtils.isNotEmpty(parts[0]) ? parts[0] : "*");
                label.put("product",parts[1]);
                label.put("label",parts[2]);
                label.put("labelType",parts[3]);
                customLabels.add(label);
            }
        });
        this.labels = customLabels;
    }
}
