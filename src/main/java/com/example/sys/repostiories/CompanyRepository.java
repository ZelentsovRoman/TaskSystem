package com.example.sys.repostiories;

import com.example.sys.models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    void deleteByCompanyId(int id);

    Company findByName(String name);
}
