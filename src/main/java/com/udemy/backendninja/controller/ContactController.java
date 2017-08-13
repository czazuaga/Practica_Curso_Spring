package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.constants.ViewConstants;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {

	private static final Log LOG = LogFactory.getLog(ContactController.class);

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;

	
	@GetMapping("/cancel")
	private String cancel() {
		LOG.info("returning to contacts view");
		return "redirect:/contacts/showcontacts";
	}

	@GetMapping("/contactform")
	private String redirectToContactForm(@RequestParam(name="id", required=false) int id,
			Model model) {
		
		ContactModel contact = new ContactModel();
		
		if(id != 0) {
			contact = contactService.findContactById(id);
		}
		
		model.addAttribute("contactModel", contact);
		LOG.info("returning to contactsForm view");
		return ViewConstants.CONTACT_FORM_VIEW;
	}

	@PostMapping("/addcontact")
	private String addContact(@ModelAttribute(name = "contactModel") ContactModel contactModel, Model model) {
		LOG.info("METHOD: addContact() -- PARAMS: " + contactModel.toString());

		if (contactService.addContact(contactModel) != null) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);
		}

		LOG.info("returning to contacts view");
	    return "redirect:/contacts/showcontacts";
	}

	@GetMapping("/showcontacts")
	private ModelAndView showContacts() {
		LOG.info("METHOD: showContacts");
		ModelAndView mav = new ModelAndView(ViewConstants.CONTACTS_VIEW);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", user.getUsername());
		mav.addObject("contacts", contactService.listAllContacts());
		LOG.info("returning to contacts view");
		return mav;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required=true) int id) {
		LOG.info("METHOD: removeContact() -- PARAMS: " + id);
		contactService.removeContact(id);
		return showContacts();
	}

}
