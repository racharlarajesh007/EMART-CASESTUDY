package com.project.emart.service;

import java.util.List;

import com.project.emart.pojo.BuyerSignupPojo;
import com.project.emart.pojo.ItemPojo;

public interface BuyerSignupService {

	
	BuyerSignupPojo validateBuyerSignup(BuyerSignupPojo buyerSignupPojo);
	BuyerSignupPojo getBuyer(Integer buyerId );
}
