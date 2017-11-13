package com.mirays.repositories;

import com.mirays.entities.Commission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommissionRepository extends CrudRepository<Commission, Long> {

    List<Commission> findAllByOrderById();

}
