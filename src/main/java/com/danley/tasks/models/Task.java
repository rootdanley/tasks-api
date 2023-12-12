package com.danley.tasks.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = Task.TABLE_NAME)
public class Task {

	public static final String TABLE_NAME = "task";

	public Task() {
	}

	public Task(Long id, User user, String description) {
		this.id = id;
		this.user = user;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@Column(name = "description", length = 255, nullable = false)
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 255)
	private String description;

}
