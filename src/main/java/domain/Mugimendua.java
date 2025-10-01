package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

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
public class Mugimendua implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer mugimenduaNumber;
	private String data;
	private float diruKantitatea;
	private String deskribapena;
	
	@ManyToOne
	private User user;
	
	public Mugimendua() {
		super();
	}
	public Mugimendua(String data, String deskribapena, User user, float diruKantitatea) {
		this.data=data;
		this.deskribapena=deskribapena;
		this.user=user;
		this.diruKantitatea=diruKantitatea;
	}
	public Mugimendua (Integer mugimenduaNumber,String data, String deskribapena, User user,float diruKantitatea ) {
		this.data=data;
		this.deskribapena=deskribapena;
		this.user=user;
		this.mugimenduaNumber=mugimenduaNumber;
		this.diruKantitatea=diruKantitatea;
	}
	public String getDeskribapena() {
		return deskribapena;
	}
	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public float getDiruKantitatea() {
		return diruKantitatea;
	}
	public void setDiruKantitatea(float diruKantitatea) {
		this.diruKantitatea = diruKantitatea;
	}
}
