package com.brs.batch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.brs.entities.BrsConsoldtStg;
import com.brs.entities.BrsError;
import com.brs.repository.BrsConsoldtStgRepository;
import com.brs.repository.BrsErrorRepository;
import com.brs.service.BrsErrorService;
import com.brs.util.BrsDataUtil;

public class BrsDataItemWriter extends ListItemWriter<List<BrsConsoldtStg>>
		implements ItemWriter<List<BrsConsoldtStg>> {

	@Autowired
	private BrsConsoldtStgRepository repo;
	
	@Autowired
	private BrsDataUtil util;
	
	@Autowired
	private BrsErrorService errorService;
	
	Logger log = LoggerFactory.getLogger(BrsDataItemWriter.class);

	@Override
	public void write(List<? extends List<BrsConsoldtStg>> items){
//		System.out.println("Inside Writer");
		long start1 = System.currentTimeMillis();
		List<BrsConsoldtStg> brsList = new ArrayList<BrsConsoldtStg>();
		try {
	
			for (List<BrsConsoldtStg> list : items) {
				brsList.addAll(list);
			}
			brsList.forEach(bs -> bs.setProcessFlag("Y"));
	
//			log.info("brsList : "+brsList.size()+"|  Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
			repo.saveAllAndFlush(brsList);
		
		}catch(Exception e) {
			
			Long refId=null;
			String bankAcNo="";
			String uniqueRefNo="";
			
			if(!brsList.isEmpty()) {
				refId=brsList.get(0).getId();
				bankAcNo=brsList.get(0).getBankAcNo();
				uniqueRefNo=brsList.get(0).getUniqueRef();
			}
			
			
			BrsError error=util.getErrorDetails("Book Update", refId, bankAcNo, uniqueRefNo, "Exception");
			errorService.saveError(error);
			log.error(error.toString(), e);
		}
		
		long end = System.currentTimeMillis();
		log.info("brsList : "+brsList.size()+"| TTP = "+(end-start1)+"| Thread=[name="+Thread.currentThread().getName()+"|id="+Thread.currentThread().getId()+"]");
		
	}
	
	
	public void writeBRS(List<BrsConsoldtStg> brsList) {
		log.info("writeBRS, brsList : "+brsList.size());
		repo.saveAllAndFlush(brsList);
	}
}
