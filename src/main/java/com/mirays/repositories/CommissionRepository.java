package com.mirays.repositories;

import com.mirays.entities.Commission;
import org.springframework.data.repository.CrudRepository;

public interface CommissionRepository extends CrudRepository<Commission, Long> {
}
