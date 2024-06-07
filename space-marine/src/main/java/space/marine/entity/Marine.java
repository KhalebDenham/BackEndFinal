package space.marine.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Marine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long marineId;

	
	@EqualsAndHashCode.Exclude
	@Column(unique = true)
	private String marineName;

	@EqualsAndHashCode.Exclude
	private String weapon;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "chapter_id", nullable = false)
	private Chapter chapter;

	@EqualsAndHashCode.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "marine_ship", joinColumns = @JoinColumn(name = "marine_id"), inverseJoinColumns = @JoinColumn(name = "ship_id")

	)
	private Set<Ship> ships = new HashSet<>();
}
