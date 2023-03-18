package me.kimsoar;

import me.kimsoar.kafka.JsonKafkaProducer;
import me.kimsoar.kafka.KafkaProducer;
import me.kimsoar.mapper.CompanyMapper;
import me.kimsoar.model.Company;
import me.kimsoar.model.Employee;
import me.kimsoar.payload.User;
import me.kimsoar.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private JsonKafkaProducer jsonKafkaProducer;

	@GetMapping({"", "/"})
	public String hello() {
		return "Hello Spring";
	}

	// ********************************* mybatis controller

	@GetMapping("/company")
	public List<Company> getAllCompany(){
		return companyMapper.getAll();
	}

	@GetMapping("/company/{id}")
	public Company getById(@PathVariable int id){
		return companyMapper.getById(id);
	}


	// ********************************* ehcache controller

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


	// ********************************* kafka controller
	// .../kafka/publish?message=hello world
	@GetMapping("/kafka/publish")
	public ResponseEntity<String> publish(@RequestParam("message") String message) {
		kafkaProducer.sendMessage(message);
		return ResponseEntity.ok("Message sent complete");
	}

	@PostMapping("/kafka/publish")
	public ResponseEntity<String> publishJson(@RequestBody User user) {
		jsonKafkaProducer.sendMessage(user);
		return ResponseEntity.ok("Json sent complete");
	}

	public static void main(String[] args) {
		SpringApplication.run(DemospringsslApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				//log.info("================= ApplicationRunner =================");
				//User user = new User();
				//user.setId(1);
				//user.setFirstName("First");
				//user.setLastName("Last");
				//jsonKafkaProducer.sendMessage(user);
			}
		};
	}

}
