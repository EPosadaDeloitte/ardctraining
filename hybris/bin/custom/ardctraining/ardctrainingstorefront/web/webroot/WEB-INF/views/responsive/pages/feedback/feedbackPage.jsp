<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>

<template:page pageTitle="${pageTitle}">
    <div>
        <c:url value ="${action}" var="x"/>
        <c:url value ="/feedback" var="refreshSite"/>

        <c:choose>
            <c:when test="${message eq true}">
                <h2>success, form was sent</h2>
            </c:when>
            <c:when test="${message eq false}">
                <h2>error, form not sent</h2>
            </c:when>
        </c:choose>

        <p>message val ${message} action ${action}</p>

        <form:form method="post" modelAttribute="feedbackForm" action="${x}" style="padding: 20px;">
            <h1>${pageTitle}</h1>
            <h2>You can submit new feedback regarding your experience with us</h2>

            choose for each label once redirected
            <formElement:formInputBox idKey="form.subject"
                labelKey="form.subject" path="subject" inputCSS="form-control"
                mandatory="true"
            />
            <formElement:formInputBox idKey="register.message"
                labelKey="form.message" path="message" inputCSS="form-control"
                mandatory="true"
                maxlength="500"
                />
             <div style="display: flex; ">
                <a href="${refreshSite}" style="padding: 5px 15px; margin-right: 10px;" role="button" class="btn btn-danger" id="cancel" >Cancel / Erase</a>
                <button type="submit" style="padding: 5px 15px;">Send</button>
             </div>
        </form:form>

        <div>
            <c:if test="${feedbacks eq null}">
                there are no submitted feedbacks
            </c:if>
            <ul>
                <c:forEach items="${feedbacks}" var="feedback">
                    <li>${feedback.subject}
                    ${feedback.message}
                    ${feedback.creationtime}
                    ${feedback.status}</li>
                </c:forEach>
            </ul>
        </div>

    </div>
</template:page>

