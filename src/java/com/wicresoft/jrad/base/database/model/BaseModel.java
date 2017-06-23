/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.wicresoft.jrad.base.database.model;

import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.ModelParam;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = null;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void generateAndAssignID() {
		ModelParam poParamAnno = (ModelParam) this.getClass().getAnnotation(
				ModelParam.class);
		IDType idType;
		if (poParamAnno != null) {
			idType = poParamAnno.generator();
			if (!IDType.assigned.equals(idType)) {
				if(!StringUtils.isNotEmpty(this.getId())){
					this.setId(IDGenerator.generateID(idType));
				}
			}
		} else {
			idType = IDType.uuid32;
			if(!StringUtils.isNotEmpty(this.getId())){
				this.setId(IDGenerator.generateID(idType));
			}
		}

	}
}