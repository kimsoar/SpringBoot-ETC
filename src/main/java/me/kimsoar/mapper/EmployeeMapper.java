package me.kimsoar.mapper;

import me.kimsoar.model.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<Employee> getAll();

    Employee getByName(@Param("name") String name);
}
