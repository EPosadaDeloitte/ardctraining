package com.ardctraining.core.search.solrfacetsearch.provider.impl;

import com.ardctraining.core.product.service.CustomProductLabelService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomProductLabelValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

    private FieldNameProvider fieldNameProvider;
    private CustomProductLabelService customProductLabelService;

    private static final String ALL_WILDCARD = "*";

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object o) throws FieldValueProviderException {
        return Collections.emptyList();
    }

    protected List<FieldValue> createFieldValues() {
        return null;
    }

    // Getters and Setters

    public FieldNameProvider getFieldNameProvider() {
        return fieldNameProvider;
    }

    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }

    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
    }
}
