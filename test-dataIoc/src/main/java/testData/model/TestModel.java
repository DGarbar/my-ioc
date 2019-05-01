package testData.model;

import javax.persistence.*;

@Entity
public class TestModel {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public TestModel() {
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
