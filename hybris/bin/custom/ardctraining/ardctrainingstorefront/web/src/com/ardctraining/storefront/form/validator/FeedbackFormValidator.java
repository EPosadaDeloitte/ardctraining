package com.ardctraining.storefront.form.validator;

import com.ardctraining.storefront.form.FeedbackForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("feedbackFormValidator")
public class FeedbackFormValidator implements Validator {

    /*
     java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because the return value of "com.ardctraining.core.model.CustomerFeedbackModel.getSubmittedDate()" is null] with root cause
java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because the return value of "com.ardctraining.core.model.CustomerFeedbackModel.getSubmittedDate()" is null

    * */

    private static final Integer MESSAGE_MIN_LENGTH = 10;

    @Override
    public boolean supports(Class<?> aClass) {
        return FeedbackForm.class.equals(aClass);
    }

    /*
    import org.springframework.jdbc.core.JdbcTemplate
    JdbcTemplate jdbcTemplate = spring.getBean("jdbcTemplate")
    try {
    int rows = jdbcTemplate.update("DROP TABLE customerFeedback;")
    } catch (Exception e) {
    println(String.format("'%s'", e.getMessage()))
    }
    * */

    @Override
    public void validate(Object o, Errors errors) {
        final FeedbackForm feedbackForm = (FeedbackForm) o;
        validateSubject(feedbackForm.getSubject(),errors);
        validateMessage(feedbackForm.getMessage(),errors);
    }

    private void validateSubject(String subject, Errors errors) {
        if (StringUtils.isEmpty(subject)) {
            //errors.rejectValue("company", "register.company.invalid");
            errors.rejectValue("subject", "subject is not in correct format");
            //return false;
        }
        //return true;
    }

    private void validateMessage(String message, Errors errors) {
        if (StringUtils.isEmpty(message) || message.length() < MESSAGE_MIN_LENGTH) {
            //errors.rejectValue("company", "register.company.invalid");
            errors.rejectValue("message", "message is not in correct format");
            //return false;
        }
        //return true;
    }
}
