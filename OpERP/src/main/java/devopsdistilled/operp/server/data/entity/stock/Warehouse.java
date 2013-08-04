package devopsdistilled.operp.server.data.entity.stock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import devopsdistilled.operp.server.data.entity.Entiti;

@Entity
public class Warehouse extends Entiti implements Serializable {
	private static final long serialVersionUID = 3048560317071734805L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long warehouseId;

	@Override
	public String toString() {
		return warehouseName;
	}

	@Column(unique = true)
	private String warehouseName;

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Override
	public Long getId() {
		return getWarehouseId();
	}

}