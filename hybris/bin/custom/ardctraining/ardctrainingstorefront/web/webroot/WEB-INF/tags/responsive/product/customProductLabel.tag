<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="customLabels" required="true" type="java.util.List<com.ardctraining.facades.product.data.CustomProductLabelData>" %>

<div class="product_details__product-custom-labels">
    <c:forEach items="${customLabels}" var="label">
        <c:if test="${label.type ne null}">
            <c:set var="labelType" value="${label.type}" />
        </c:if>
        <span class="product_details__product-custom-labels__item label ${label.type}">
            ${label.label}
        </span>
    </c:forEach>
</div>