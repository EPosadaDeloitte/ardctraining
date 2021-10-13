package com.ardctraining.core.feedback.dao.impl;

import com.ardctraining.core.enums.FeedbackStatusEnum;
import com.ardctraining.core.feedback.dao.FeedbackDao;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DefaultFeedbackDao implements FeedbackDao {

    private FlexibleSearchService flexibleSearchService;
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultFeedbackDao.class));

    // Queries
    private static final String SELECT =
            "SELECT {" + ItemModel.PK + "}";
    private static final String FIND_BY_CUSTOMER_UNREAD_FEEDBACKS =
            SELECT +
            "FROM {" + CustomerFeedbackModel._TYPECODE + "}" +
            "WHERE {" + CustomerFeedbackModel.CUSTOMER + "} = ?customer";
            //"WHERE {" + CustomerFeedbackModel.CUSTOMER + "} = ?customer AND " +
            //" {" + CustomerFeedbackModel.READ + "} = ?read";
            //" {"+ CustomerFeedbackModel.STATUS +"} = ?status"; ???????

    @Override
    public List<CustomerFeedbackModel> findByCustomerUnreadFeedbacks(CustomerModel customerModel) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_BY_CUSTOMER_UNREAD_FEEDBACKS);
        query.addQueryParameter("customer", customerModel);
        // Collections.emptyList().stream().filter((item) -> { return });
        return findResult(query)
                .stream()
                .filter(
                        (CustomerFeedbackModel customerFeedback) ->
                                customerFeedback.getStatus() == FeedbackStatusEnum.NOT_READ || customerFeedback.getStatus() == FeedbackStatusEnum.PAST_DUE
                ).collect(Collectors.toList());
    }

    private List<CustomerFeedbackModel> findResult(FlexibleSearchQuery query) {
        final SearchResult result = getFlexibleSearchService().search(query); // searches in model, given a lexible query
        if (!Objects.isNull(result) && CollectionUtils.isNotEmpty(result.getResult())) { // returns list of product labels successfully
            return result.getResult();
        }
        LOG.warning("Unable to find results for "+this.getClass().getSimpleName());
        return Collections.emptyList();
    }

    // Getters and Setters

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

}
