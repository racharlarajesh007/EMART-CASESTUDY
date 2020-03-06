package com.project.emart.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.emart.dao.BuyerSignupDao;
import com.project.emart.entity.BillDetailsEntity;
import com.project.emart.entity.BillEntity;
import com.project.emart.entity.BuyerSignupEntity;
import com.project.emart.entity.CategoryEntity;
import com.project.emart.entity.ItemEntity;
import com.project.emart.entity.SellerSignupEntity;
import com.project.emart.entity.SubCategoryEntity;
import com.project.emart.pojo.BillDetailsPojo;
import com.project.emart.pojo.BillPojo;
import com.project.emart.pojo.BuyerSignupPojo;
import com.project.emart.pojo.CategoryPojo;
import com.project.emart.pojo.ItemPojo;
import com.project.emart.pojo.SellerSignupPojo;
import com.project.emart.pojo.SubCategoryPojo;

@Service
public class BuyerSignupServiceImpl implements BuyerSignupService {
	
	static Logger LOG = Logger.getLogger(BuyerSignupServiceImpl.class.getClass());
	
	@Autowired
	BuyerSignupDao buyerSignupDao;

	@Override
	
	//validating buyer credentials
	
	public BuyerSignupPojo validateBuyerSignup(BuyerSignupPojo buyerSignupPojo) {
		
		LOG.info("entered validateBuyer()");
		
		BuyerSignupEntity buyerSignupEntity = buyerSignupDao.findByUsernameAndPassword(buyerSignupPojo.getUsername(),
				buyerSignupPojo.getPassword());
		
        //checking for the existence of buyer entity
		
		if (buyerSignupEntity != null) {
			Set<BillEntity> allBillsEntity = buyerSignupEntity.getAllBills();

			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();
  
			//copying all entities into respective pojos
			
			
			for (BillEntity billEntity : allBillsEntity) {
				Set<BillDetailsEntity> allBillDetailsEntity = billEntity.getAllBillDetails();
				Set<BillDetailsPojo> allBillDetailsPojo = new HashSet<BillDetailsPojo>();

				for (BillDetailsEntity billDetailsEntity : allBillDetailsEntity) {

					
					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubcategory();
					CategoryEntity categoryEntity = subCategoryEntity.getCategory();
					SellerSignupEntity sellerEntity = itemEntity.getSeller();

					
					SellerSignupPojo sellerPojo = new SellerSignupPojo(sellerEntity.getId(), sellerEntity.getUsername(),
							sellerEntity.getPassword(), sellerEntity.getCompany(), sellerEntity.getBrief(),
							sellerEntity.getGst(), sellerEntity.getAddress(), sellerEntity.getEmail(),
							sellerEntity.getWebsite(), sellerEntity.getContact());
					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
							categoryEntity.getBrief());

					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
							subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
							subCategoryEntity.getGstPercent());

					ItemPojo itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), sellerPojo,
							subCategoryPojo, itemEntity.getPrice(), itemEntity.getDescription(), itemEntity.getStock(),
							itemEntity.getRemarks(), itemEntity.getImage()

					);

					BillDetailsPojo billDetailsPojo = new BillDetailsPojo(billDetailsEntity.getId(), null, itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(billEntity.getId(), billEntity.getType(), billEntity.getDate(),
						billEntity.getRemarks(), billEntity.getAmount(), null, allBillDetailsPojo);

				allBillPojo.add(billPojo);
			}
			buyerSignupPojo = new BuyerSignupPojo(buyerSignupEntity.getId(), buyerSignupEntity.getUsername(),
					buyerSignupEntity.getPassword(), buyerSignupEntity.getEmail(), buyerSignupEntity.getMobile(),
					buyerSignupEntity.getDate(), allBillPojo);
		}
		
		LOG.info("Exited validateBuyer()");
		BasicConfigurator.resetConfiguration();
		
		return buyerSignupPojo;
	}

	@Override
	//retrieve buyerpojo based on Id
	public BuyerSignupPojo getBuyer(Integer buyerId) {
		
		LOG.info("Entered getBuyer()");
		
		BuyerSignupPojo buyerSignupPojo = new BuyerSignupPojo();
		Optional<BuyerSignupEntity> optional = buyerSignupDao.findById(buyerId);
		

		if (optional.isPresent()) {
			BuyerSignupEntity buyerSignupEntity = optional.get();
			Set<BillEntity> allBillsEntity = buyerSignupEntity.getAllBills();

			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();

			for (BillEntity billEntity : allBillsEntity) {
				Set<BillDetailsEntity> allBillDetailsEntity = billEntity.getAllBillDetails();
				Set<BillDetailsPojo> allBillDetailsPojo = new HashSet<BillDetailsPojo>();

				for (BillDetailsEntity billDetailsEntity : allBillDetailsEntity) {

					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubcategory();
					CategoryEntity categoryEntity = subCategoryEntity.getCategory();
					SellerSignupEntity sellerEntity = itemEntity.getSeller();

					SellerSignupPojo sellerPojo = new SellerSignupPojo(sellerEntity.getId(), sellerEntity.getUsername(),
							sellerEntity.getPassword(), sellerEntity.getCompany(), sellerEntity.getBrief(),
							sellerEntity.getGst(), sellerEntity.getAddress(), sellerEntity.getEmail(),
							sellerEntity.getWebsite(), sellerEntity.getContact());
					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
							categoryEntity.getBrief());

					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
							subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
							subCategoryEntity.getGstPercent());

					ItemPojo itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), sellerPojo,
							subCategoryPojo, itemEntity.getPrice(), itemEntity.getDescription(), itemEntity.getStock(),
							itemEntity.getRemarks(), itemEntity.getImage()

					);

					BillDetailsPojo billDetailsPojo = new BillDetailsPojo(billDetailsEntity.getId(), null, itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(billEntity.getId(), billEntity.getType(), billEntity.getDate(),
						billEntity.getRemarks(), billEntity.getAmount(), null, allBillDetailsPojo);

				allBillPojo.add(billPojo);
			}
			buyerSignupPojo = new BuyerSignupPojo(buyerSignupEntity.getId(), buyerSignupEntity.getUsername(),
					buyerSignupEntity.getPassword(), buyerSignupEntity.getEmail(), buyerSignupEntity.getMobile(),
					buyerSignupEntity.getDate(), allBillPojo);
		}
		
		LOG.info("Exited getBuyer()");
		BasicConfigurator.resetConfiguration();
		
		return buyerSignupPojo;

	}

}
