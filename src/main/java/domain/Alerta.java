package domain;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Alerta implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer idAlerta;
	private String nondik;
	private String nora;
	private Date data;
	private boolean eginda;
	
	@ManyToOne
	private Traveler traveler;
	
	
	public Alerta() {
		super();
	}
	public Alerta(Integer idAlerta, String nondik, String nora, Date data, Traveler traveler) {
		super();
		this.idAlerta=idAlerta;
		this.setNondik(nondik);
		this.setNora(nora);
		this.data=data;
		this.traveler=traveler;
		this.eginda=false;
	}
	public Alerta(String nondik, String nora, Date data, Traveler traveler) {
		super();
		this.setNondik(nondik);
		this.setNora(nora);
		this.data=data;
		this.traveler=traveler;
		this.eginda=false;
	}
	public Integer getIdAlerta() {
		return this.idAlerta;
	}
	public void setIdAlerta(Integer newIdAlerta) {
		this.idAlerta=newIdAlerta;
	}
	public String getNondik() {
		return nondik;
	}
	public void setNondik(String nondik) {
		this.nondik = nondik;
	}
	public String getNora() {
		return nora;
	}
	public void setNora(String nora) {
		this.nora = nora;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isEginda() {
		return eginda;
	}
	public void setEginda(boolean eginda) {
		this.eginda = eginda;
	}
	public boolean alertEqualsRide(String nondik, String nora, Date data) {
		if(this.nondik.equals(nondik)&&this.nora.equals(nora)&&this.data.equals(data)) {
			return true;
		}
		return false;
	}
}
