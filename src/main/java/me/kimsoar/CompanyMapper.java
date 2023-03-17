package me.kimsoar;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {

    List<Company> getAll();

    Company getById(@Param("id") int id);

}
