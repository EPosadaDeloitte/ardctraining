package com.ardctraining.core.product.dao.impl;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.dao.CustomProductLabelDao;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class DefaultCustomProductLabelDao implements CustomProductLabelDao {

    private FlexibleSearchService flexibleSearchService;
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultCustomProductLabelDao.class));

    private static final String SELECT =
            "SELECT {" + ItemModel.PK + "}";
    private static final String FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_QUERY =
            "SELECT {" + ItemModel.PK + "}" +
            "FROM {" + CustomProductLabelModel._TYPECODE + "}" +
            "WHERE {" + CustomProductLabelModel.CUSTOMER + "} = ?customer AND " +
            " {" + CustomProductLabelModel.PRODUCT + "} = ?product";
    private static final String FIND_EXPIRED_LABELS_QUERY =
            "SELECT {" + ItemModel.PK + "}" +
            "FROM {" + CustomProductLabelModel._TYPECODE + "}" +
            "WHERE {" + CustomProductLabelModel.VALIDITYDATE + "} < ?now";
    private static final String FIND_BY_CUSTOMER_AND_PRODUCT_AND_NULLCUSTOMER =
            SELECT +
            "FROM {" + CustomProductLabelModel._TYPECODE + "}" +
            "WHERE {" + CustomProductLabelModel.CUSTOMER + "} = ?customer AND " +
            " {" + CustomProductLabelModel.PRODUCT + "} = ?product AND " +
            "{"+ CustomProductLabelModel.CUSTOMER +"} = null??";
    @Override
    public List<CustomProductLabelModel> findByCustomerAndProduct(final CustomerModel customer, final ProductModel product) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_QUERY);
        query.addQueryParameter("customer", customer);
        query.addQueryParameter("product", product);
        return findResult(query);
    }

    @Override
    public List<CustomProductLabelModel> findExpired(final Date now) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_EXPIRED_LABELS_QUERY);
        query.addQueryParameter("now", now);
        return findResult(query);
    }

    @Override
    public List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(CustomerModel customer, ProductModel product) {
        return null;
    }

    private List<CustomProductLabelModel> findResult(FlexibleSearchQuery query) {
        final SearchResult result = getFlexibleSearchService().search(query); // searches in model, given a lexible query
        if (!Objects.isNull(result) && CollectionUtils.isNotEmpty(result.getResult())) { // returns list of product labels successfully
            return result.getResult();
        }
        LOG.warning("Unable to find results for custom product labels");
        return Collections.emptyList();
    }

    // Getters and Setters
    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    public static String getFindLabelsByCustomerAndProductQuery() {
        return FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_QUERY;
    }
}
