package me.kimsoar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableCaching
public class DemospringsslApplication {

	private static Logger log = LoggerFactory.getLogger(DemospringsslApplication.class);
	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping({"", "/"})
	public String hello() {
		return "Hello Spring";
	}

	@GetMapping("/company")
	public List<Company> getAllCompany(){
		return companyMapper.getAll();
	}

	@GetMapping("/company/{id}")
	public Company getById(@PathVariable int id){
		return companyMapper.getById(id);
	}

	@GetMapping("/nocache/{username}")
	public Employee getNocacheMember(@PathVariable String username) {
		long start = System.currentTimeMillis(); // 수행시간 측정
		Employee member = employeeService.getByNameWithNoCache(username);
		long end = System.currentTimeMillis();

		log.info(username+ "의 NoCache 수행시간 : "+ (end - start)); // 수행시간 logging

		return member;
	}

	@GetMapping("/cache/{username}")
	public Employee getCacheMember(@PathVariable String username) {
		long start = System.currentTimeMillis(); // 수행시간 측정
		Employee member = employeeService.getByNameWithCache(username);
		long end = System.currentTimeMillis();

		log.info(username+ "의 Cache 수행시간 : "+ (end - start)); // 수행시간 logging

		return member;
	}

	@GetMapping("/cache1/{username}")
	public Employee getCacheMemberOther(@PathVariable String username) {
		long start = System.currentTimeMillis(); // 수행시간 측정
		Employee member = employeeService.getByNameOtherWithCache(username);
		long end = System.currentTimeMillis();

		log.info(username+ "의 Cache 수행시간 : "+ (end - start)); // 수행시간 logging

		return member;
	}


	@GetMapping("/cache/refresh/{username}")
	public String refresh(@PathVariable String username){
		employeeService.refresh(username); // 캐시제거
		log.info(username+ "의 Cache Clear!");
		return username + " cache clear!";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemospringsslApplication.class, args);
	}

}
