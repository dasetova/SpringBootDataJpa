package com.dasetova.springstudy.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="bills")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String description;
	
	
	private String observation;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	@PrePersist
	public void prePresist() {
		createAt = new Date();
	}
	
	// fetch -> como cargar la relación? EAGER de una LAZY -> Solo cuando se solicite
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Customer customer;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="bill_id") //Necesario porque no tenemos la relacion en ambos sentidos
	private List<BillItem> billItems;
	
	public Bill() {
		this.billItems = new ArrayList<BillItem>();
	}
	
	public Long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public String getObservation() {
		return observation;
	}
	public Date getCreateAt() {
		return createAt;
	}
	
	@XmlTransient
	public Customer getCustomer() {
		return customer;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<BillItem> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}
	public void addBillItem(BillItem billItem) {
		this.billItems.add(billItem);
	}
	
	public Double getTotal() {
		Double total=0.0;
		
		for(BillItem item : billItems) {
			total+=item.calculateValue();
		}
		return total;
	}
	
	
}
