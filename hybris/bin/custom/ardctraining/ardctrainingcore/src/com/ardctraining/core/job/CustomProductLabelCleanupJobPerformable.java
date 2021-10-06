package com.ardctraining.core.job;

import com.ardctraining.core.model.CustomProductLabelCleanupCronjobModel;
import com.ardctraining.core.model.CustomProductLabelCleanupEmailProcessModel;
import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.service.CustomProductLabelService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.exceptions.ModelRemovalException;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomProductLabelCleanupJobPerformable extends AbstractJobPerformable<CustomProductLabelCleanupCronjobModel> {
    private static final Logger LOG = Logger.getLogger(CustomProductLabelCleanupJobPerformable.class);

    private CustomProductLabelService customProductLabelService;
    private BusinessProcessService businessProcessService;
    private BaseSiteService baseSiteService;
    private TimeService timeService;
    private static final String FIELD_SEPARATOR = "|";

    @Override
    public PerformResult perform(CustomProductLabelCleanupCronjobModel customProductLabelCleanupCronjobModel) {
        LOG.debug("entering CustomProductLabelCleanupJobPerformable::perform");
        final List<CustomProductLabelModel> expiredLabels = getCustomProductLabelService().findExpired();
        LOG.info("labels to delete");
        try {
            final Set<String> labels = getCustomLabels(expiredLabels);
            final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm:ss.S");
            final Date now = getTimeService().getCurrentTime();

            modelService.removeAll(expiredLabels);
            final CustomProductLabelCleanupEmailProcessModel emailProcessModel = getBusinessProcessService().createProcess(
                    new StringBuilder().append("customProductLabelCleanupEmailProcess").append("-").append(dateFormat.format(now)).toString(),
                    "customLabelCleanupEmailProcess"
            );

            emailProcessModel.setLanguage(customProductLabelCleanupCronjobModel.getSessionLanguage());
            emailProcessModel.setCustomLabels(labels);
            emailProcessModel.setSite(getBaseSiteService().getBaseSiteForUID("electronics"));

            modelService.save(emailProcessModel);

            getBusinessProcessService().startProcess(emailProcessModel);
        } catch (ModelRemovalException e){
            LOG.error("Unable to delete custom labels");
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.SUCCESS,CronJobStatus.FINISHED);
    }

    private Set<String> getCustomLabels (final List<CustomProductLabelModel> labels) {
        return labels
                .stream()
                .map((CustomProductLabelModel label)  -> {
                    return new StringBuilder()
                            .append(Objects.isNull(label.getCustomer()) ? StringUtils.EMPTY : label.getCustomer().getUid())
                            .append(FIELD_SEPARATOR)
                            .append(label.getProduct().getCode())
                            .append(FIELD_SEPARATOR)
                            .append(label.getLabel())
                            .append(FIELD_SEPARATOR)
                            .append(label.getLabelType().getCode())
                            .toString();
                }).collect(Collectors.toSet());
    }

    @Override
    public boolean isAbortable() {
        return Boolean.TRUE;
    }

    // Getters and Setters
    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
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

    /*
    *
    *
    <alias name="defaultCustomLabelValueProvider" alias="customLabelValueProvider"/>
	<bean id="defaultCustomLabelValueProvider"
		  class="com.ardctraining.core.job.CustomProductLabelCleanupJobPerformable"
		  parent="abstractPropertyFieldValueProvider"
	>
		<property name="defaultCustomLabelValueProvider" ref="customLabelValueProvider"/>
	</bean>
    * */

}
