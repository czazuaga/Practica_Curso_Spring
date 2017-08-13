package com.udemy.backendninja.service;

import java.util.List;

import com.udemy.backendninja.model.ContactModel;

public interface ContactService {

	public abstract ContactModel addContact(ContactModel model);

	public abstract List<ContactModel> listAllContacts();

	public abstract ContactModel findContactById(int id);
	
	public abstract void removeContact(int id);
	
	
}
