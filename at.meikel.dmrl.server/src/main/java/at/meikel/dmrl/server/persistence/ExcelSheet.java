package at.meikel.dmrl.server.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ExcelSheet implements Serializable {

	// http://www.zabada.com/tutorials/hibernate-and-jpa-with-spring-example
	// http://schuchert.wikispaces.com/JPA+Tutorial+1+-+Getting+Started

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date timestamp;
	private String url;
	private byte[] data;
	private STATE state;

	public static enum STATE {
		OK, CORRUPTED
	}

	public ExcelSheet() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Lob
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}
}
