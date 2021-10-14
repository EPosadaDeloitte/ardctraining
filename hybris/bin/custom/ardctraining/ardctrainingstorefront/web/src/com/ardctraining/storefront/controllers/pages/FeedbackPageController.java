package com.ardctraining.storefront.controllers.pages;

import com.ardctraining.facades.feedback.FeedbackFacade;
import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import com.ardctraining.storefront.controllers.ControllerConstants;
import com.ardctraining.storefront.form.FeedbackForm;
import com.ardctraining.storefront.form.validator.FeedbackFormValidator;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Controller for customer feedback page
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackPageController extends AbstractPageController {

    @Resource(name = "feedbackFacade")
    private FeedbackFacade feedbackFacade;

    @Resource(name = "feedbackFormValidator")
    private FeedbackFormValidator feedbackFormValidator;

    private static final String REDIRECT_FEEDBACK_VIEW_URL = REDIRECT_PREFIX + "/feedback";
    // private static final String REDIRECT_SAVE_VIEW_URL = REDIRECT_PREFIX + "/feedback";

    // GET methods

    @RequestMapping(method = RequestMethod.GET)
    public String showFeedbacks(final Model model, @RequestParam(value = "message", required = false) boolean message/* @ModelAttribute("message") boolean message2*/) throws CMSItemNotFoundException {
        final ContentPageModel contentPage = getContentPageForLabelOrId("feedback");
        storeCmsPageInModel(model, contentPage);
        setUpMetaDataForContentPage(model, contentPage);

        //System.out.println("I was called, my attribute message is "+model.getAttribute("message"));
        //System.out.println("message as path variable "+message2);
        System.out.println("Message is "+message);

        model.addAttribute("feedbacks",feedbackFacade.getFeedbackForms());
        model.addAttribute("feedbackForm", new FeedbackForm());
        model.addAttribute("action","/feedback/processSubmission");
        model.addAttribute("pageTitle","Submit New Feedback");
        return ControllerConstants.Views.Pages.Feedback.FeeedbackPage;
    }

    // POST methods

    @PostMapping(value = "/processSubmission")
    public String processSubmission (final Model model, @ModelAttribute final FeedbackForm feedbackForm, final BindingResult bindingResult, final RedirectAttributes redirectAttributes) { // send back a response, success/fail
        // calls facade to save the submission, or show an error in case it is needed
        getFeedbackFormValidator().validate(feedbackForm, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", false);
        } else {
            final CustomerFeedbackData customerFeedbackData;
            customerFeedbackData = createCustomerFeedbackData(feedbackForm);
            System.out.println("form subject "+customerFeedbackData.getSubject());
            System.out.println("form message "+customerFeedbackData.getMessage());
            if(getFeedbackFacade().saveForm(customerFeedbackData)) {
                //redirectAttributes.addAttribute("message", true);
                redirectAttributes.addFlashAttribute("message", true);
            }
            else { // enters if the form was in correct format, but could not be saved
                //redirectAttributes.addAttribute("message", false);
                redirectAttributes.addFlashAttribute("message", false);
            }
            // redirectAttributes.addAttribute()

        }
        // return "redirect:/feedback";
        return REDIRECT_FEEDBACK_VIEW_URL;
    }

    // helper methods
    public CustomerFeedbackData createCustomerFeedbackData(FeedbackForm feedbackForm){
        final CustomerFeedbackData customerFeedbackData = new CustomerFeedbackData();
        customerFeedbackData.setSubject(feedbackForm.getSubject());
        customerFeedbackData.setMessage(feedbackForm.getMessage());
        return customerFeedbackData;
    }

    // Getters and Setters
    public FeedbackFacade getFeedbackFacade() {
        return feedbackFacade;
    }

    public void setFeedbackFacade(FeedbackFacade feedbackFacade) {
        this.feedbackFacade = feedbackFacade;
    }

    public FeedbackFormValidator getFeedbackFormValidator() {
        return feedbackFormValidator;
    }

    public void setFeedbackFormValidator(FeedbackFormValidator feedbackFormValidator) {
        this.feedbackFormValidator = feedbackFormValidator;
    }
}
