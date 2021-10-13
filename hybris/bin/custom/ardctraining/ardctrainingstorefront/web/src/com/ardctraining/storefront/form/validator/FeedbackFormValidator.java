package com.ardctraining.storefront.form.validator;

import com.ardctraining.storefront.form.FeedbackForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FeedbackFormValidator implements Validator {

    private static final Integer MESSAGE_MIN_LENGTH = 10;

    @Override
    public boolean supports(Class<?> aClass) {
        return FeedbackForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final FeedbackForm feedbackForm = (FeedbackForm) o;
        validateSubject(feedbackForm.getSubject());
        validateMessage(feedbackForm.getMessage());
    }

    private boolean validateSubject(String subject) {
        if (StringUtils.isEmpty(subject)) {
            //errors.rejectValue("company", "register.company.invalid");
            return false;
        }
        return true;
    }

    private boolean validateMessage(String message) {
        if (StringUtils.isEmpty(message) || message.length() < MESSAGE_MIN_LENGTH) {
            //errors.rejectValue("company", "register.company.invalid");
            return false;
        }
        return true;
    }
}
