package me.kimsoar.service;

import me.kimsoar.mapper.EmployeeMapper;
import me.kimsoar.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeMapper.getAll();
    }

    public Employee getByNameWithNoCache(String name) {
        slowQuery(2000);
        return employeeMapper.getByName(name);
    }

    @Cacheable(value="findMemberCache", key = "#name") // 해당 캐시 사용
    public Employee getByNameWithCache(String name) {
        slowQuery(2000);
        return employeeMapper.getByName(name);
    }

    @Cacheable(value="findMember", key = "#name") // 해당 캐시 사용
    public Employee getByNameOtherWithCache(String name) {
        slowQuery(2000);
        return employeeMapper.getByName(name);
    }

    @CacheEvict(value = "findMemberCache", key="#name") //해당 캐시 삭제
    public String refresh(String name) {
        return name + "님의 Cache Clear !";
    }

    // 빅쿼리를 돌린다는 가정
    private void slowQuery(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
