package testData.model;

import javax.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
public class TestModel {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@NaturalId
	private String surname;

	public TestModel() {
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
