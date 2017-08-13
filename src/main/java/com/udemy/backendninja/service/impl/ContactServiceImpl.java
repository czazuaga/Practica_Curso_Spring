package com.udemy.backendninja.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.converter.ContactConverter;
import com.udemy.backendninja.entity.Contact;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.repository.ContactRepository;
import com.udemy.backendninja.service.ContactService;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {

	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;

	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;

	@Override
	public ContactModel addContact(ContactModel contactModel) {
		Contact contact = contactRepository.save(contactConverter.convertContactModel2Contact(contactModel));
		return contactConverter.convertContact2ContactModel(contact);
	}

	@Override
	public List<ContactModel> listAllContacts() {
		List<Contact> contactsList = contactRepository.findAll();
		List<ContactModel> contactModelsList = new ArrayList<ContactModel>();
		for (Contact contact : contactsList) {
			contactModelsList.add(contactConverter.convertContact2ContactModel(contact));
		}
		return contactModelsList;
	}

	@Override
	public ContactModel findContactById(int id) {
		Contact contact = contactRepository.findById(id);
		return contactConverter.convertContact2ContactModel(contact);
	}

	@Override
	public void removeContact(int id) {
		Contact contact = contactRepository.findById(id);
		if(contact != null)
		contactRepository.delete(contact);
	}

}
