package com.ron.personrecord.web.beans.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.daimler.vbto.utilities.dtos.VbtoDTO;
import com.daimler.vbto.utilities.exceptions.VbtoException;
import com.daimler.vbto.utilities.services.VbtoServiceFactory;
import com.daimler.vbto.web.beans.common.DataScrollerBean;
import com.ron.personrecord.masterdata.api.dtos.PersonRecordDTO;
import com.ron.personrecord.masterdata.api.services.PersonRecordLocal;

@ManagedBean
@ViewScoped
public class PersonRecordBean extends DataScrollerBean
{
	private static final long serialVersionUID = 1L;
	private List<PersonRecordDTO> personRecordDTOListInBCF;
	private Map<Long, Boolean> checked;
	private PersonRecordLocal personRecordLocal;
	private PersonRecordDTO personRecordEditDTO;
	private PersonRecordDTO personRecordNavigationDTO;
	
	@Override
	@PostConstruct
	public void init()
	{	
		super.init();
		checked = new HashMap<Long, Boolean>();
	}
	
	public String navigatePage()
	{
		try 
		{
			Long personId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("personRecord"));
			
			for(PersonRecordDTO personRecordDTO : personRecordDTOListInBCF) 
			{
				if(personId.equals(personRecordDTO.getId())) 
				{
					personRecordEditDTO = personRecordDTO;
					break;
				}
			}
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("PERSON_RECORD_DTO", personRecordEditDTO);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return "personedit.xhtml" + "?faces-redirect=true";
	}
	
	public void editThePersonRecord(PersonRecordDTO personDTO)
	{
		this.personRecordEditDTO = personDTO;
		System.out.println("Person Record object in EP method : " + personRecordEditDTO);
	}
	
	public void deleteRecordsBasedOncheckBoxSelection()
	{
		try 
		{
			personRecordLocal = VbtoServiceFactory.getService(PersonRecordLocal.class);
			personRecordLocal.deleteRecordsBCF(checked);
		} 
		catch (VbtoException e) 
		{
			handleException(e);
		}
		this.refresh();
	}
	
	@Override
	public List<? extends VbtoDTO> getTotalListData() 
	{
		PersonRecordLocal personRecordLocal;
		try 
		{
			personRecordLocal = VbtoServiceFactory.getService(PersonRecordLocal.class);
			personRecordDTOListInBCF  = personRecordLocal.getAllPersonRecords();
		} 
		catch (VbtoException e) 
		{
			handleException(e);
		}
		return personRecordDTOListInBCF;
	}
	
	
	
	// Getters and Setters
	public List<PersonRecordDTO> getPersonRecordDTOListInBCF() 
	{
		System.out.println("Person Record DTO in Managed Bean Getter method : " + personRecordDTOListInBCF);
		return personRecordDTOListInBCF;
	}

	public void setPersonRecordDTOListInBCF(List<PersonRecordDTO> personRecordDTOListInBCF) 
	{
		this.personRecordDTOListInBCF = personRecordDTOListInBCF;
	}
	
	public Map<Long, Boolean> getChecked() 
	{
		return checked;
	}

	public void setChecked(Map<Long, Boolean> checked) 
	{
		this.checked = checked;
	}

	public PersonRecordDTO getPersonRecordEditDTO() 
	{
		return personRecordEditDTO;
	}

	public void setPersonRecordEditDTO(PersonRecordDTO personRecordEditDTO) 
	{
		this.personRecordEditDTO = personRecordEditDTO;
	}

	public PersonRecordDTO getPersonRecordNavigationDTO() 
	{
		return personRecordNavigationDTO;
	}

	public void setPersonRecordNavigationDTO(PersonRecordDTO personRecordNavigationDTO) 
	{
		this.personRecordNavigationDTO = personRecordNavigationDTO;
	}
	
	

	/*
	@PostConstruct
	public void init()
	{
		personList = new ArrayList<Person>();
		personList.add(new Person(1, "ABC", "XYZ"));
		personList.add(new Person(2, "DEF", "UVW"));
		personList.add(new Person(3, "GHI", "RST"));
		personList.add(new Person(4, "JKL", "OPQ"));
	}
	
	public List<Person> getPersonList() 
	{
		return personList;
	}

	public void setPersonList(List<Person> personList) 
	{
		this.personList = personList;
	}
	*/
}
