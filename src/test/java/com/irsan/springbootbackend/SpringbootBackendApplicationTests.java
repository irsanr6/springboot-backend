package com.irsan.springbootbackend;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.repository.CronJobTriggerRepository;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.CompressionUtil;
import com.irsan.springbootbackend.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class SpringbootBackendApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DataEmployeeRepository dataEmployeeRepository;
    @Autowired
    private CronJobTriggerRepository cronJobTriggerRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e :
                employees) {
            log.info("Data {} ", e);
        }
    }

    @Test
    void saveEmployee() {
        String emailExt = "@gmail.com";
        Employee employee1 = Employee.builder()
                .firstName("Hendra")
                .lastName("Prasetyo")
                .email("hendra" + emailExt)
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Ikhsan")
                .lastName("Okto")
                .email("Okto" + emailExt)
                .build();
        Employee employee3 = Employee.builder()
                .firstName("Galang")
                .lastName("Saputra")
                .email("galang" + emailExt)
                .build();

        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        log.info("Data berhasil ditambahkan");
    }

    @Test
    void currentDateTime() {
        Helper.dateTimeNow();
    }

    @Test
    void threadSleep() {
        log.info("Kita coba");
        try {
            Thread.sleep(30000);
            log.info("Dari Thread sleep");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void dataEmployeeUpdate() {
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee :
                employeeList) {
            Optional<DataEmployee> dataEmployee = dataEmployeeRepository.findByEmployeeId(employee.getEmployeeId());
            if (dataEmployee.isEmpty()) {
                DataEmployee dataEmpCreate = DataEmployee.builder()
                        .employeeId(employee.getEmployeeId())
                        .address("-")
                        .phoneNumber("-")
                        .nik("-")
                        .isAktif("-")
                        .position("-")
                        .build();
                DataEmployee dataEmpSave = dataEmployeeRepository.save(dataEmpCreate);

                Helper.ok("Data berhasil ditambah", EmployeeResponse.builder()
                        .employeeId(employee.getEmployeeId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .fullName(employee.getFirstName() + " " + employee.getLastName())
                        .email(employee.getEmail())
                        .address(dataEmpSave.getAddress())
                        .phoneNumber(dataEmpSave.getPhoneNumber())
                        .nik(dataEmpSave.getNik())
                        .isAktif(dataEmpSave.getIsAktif())
                        .position(dataEmpSave.getPosition())
                        .build());
            }
        }
        Helper.ok("Semua data telah di simpan");
    }

    @Test
    void findCronValue() {
        log.info("Cron: {}", cronJobTriggerRepository.findByCodeTrigger("AA").get().getCronValue());

    }

    @Test
    void loop() {
        Long[] employeeId = {1L, 2l, 10L};

        List<Employee> employees = new ArrayList<>();

        for (Long id :
                employeeId) {
            log.info("employeeId {}", id);
            employees.add(Optional.ofNullable(employeeRepository.findByEmployeeId(id)).map(Optional::get).orElseGet(null));
        }
        log.info("employee {}", employees);
    }

    @Test
    void equalsCharSequenceString() {
        CharSequence firstString = "baeldung";
        String secondString = "baeldung";

        if (firstString == secondString) {
            log.info("equals");
        } else {
            log.info("not equals");
        }
    }

    @Test
    void testLagi() {
        List<String> input = new LinkedList<>(Arrays.asList("a", "b", "c"));
        List<CharSequence> result;

//    result = input; // <-- Type mismatch: cannot convert from List<String> to List<CharSequence>
        result = new ArrayList<>(input);

        System.out.println(result);
    }

    @Test
    void streamJoin() {
        Stream<String> words = Arrays.asList("Irsan", "Ramadhan").stream();
        String fullname = words.collect(Collectors.joining(" "));
        log.info("Full Name: {}", fullname);
    }

    @Test
    void countString() {
        for (int i = 0; i < 11; i++) {
            String id = UUID.randomUUID().toString().replace("-", "");
            long countId = id.chars().count();
            log.info("Count: {}", countId);
        }
    }

    @Test
    void traversWordByWord() {
        String awkward = "Irsan Ramadhan_Noverry-Ambo:Fachrul;Hidayat.Galang,Saputra@Dinda!Aulia(Nabila Anggraini)Inwan";
        String delimiter = "." + "," + " " + ";" + ":" + "+" + "(" + ")" + "{" + "}" + "[" + "]" + "!" + "@" + "?" + "/" + "*" + "&" + "%" + "$" + "#" + "=" + "-" + "_";
        List<String> news = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(awkward, delimiter);
        while (st.hasMoreTokens()) {
            news.add(st.nextToken());
        }
        String words = news.stream()
                .map(String::intern)
                .collect(Collectors.joining("-"));
        log.info("{}", StringUtils.lowerCase(words));
    }

    @Test
    void asciiToAlphabet() throws UnsupportedEncodingException {
        int num = 65;
        char alph = (char) num;

        List<Integer> asciis = new ArrayList<>();

        String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        byte[] bytes = name.getBytes(StandardCharsets.US_ASCII);

        String character = "Irsan";
        for (char c :
                character.toCharArray()) {
            asciis.add((int) c);
        }

        log.info("ascii-{}", asciis);
        log.info("alph-{}", alph);
        log.info("bytes-{}", bytes);
    }

    @Test
    void deleteEmployee() {
        Long id = 41L;
        Optional<Employee> employee = employeeRepository.findByEmployeeId(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            employeeRepository.delete(emp);
            log.info("Employe with id {} has deleted", emp.getEmployeeId());
        } else {
            log.info("Fail: not found");
        }
    }

    @Test
    void setPassword() throws IOException {
        List<Employee> employeeList = employeeRepository.findAll();
        String password;
        for (Employee emp :
                employeeList) {
            Optional<Employee> employee = employeeRepository.findByEmployeeId(emp.getEmployeeId());
            if (employee.isPresent()) {
                Employee empSet = employee.get();
                password = StringUtils.lowerCase(String.join(".", empSet.getLastName(), empSet.getFirstName()));
                empSet.setPassword(passwordEncoder.encode(password));
                empSet.setEncodePassword(CompressionUtil.compressB64(StringUtils.lowerCase(password)));
                employeeRepository.save(empSet);
            }
        }
    }

    String testStr = "Irsan Ramadhan";

    @Test
    void compressByte() throws IOException {
        byte[] input = testStr.getBytes();
        byte[] op = CompressionUtil.compress(input);
        System.out.println("original data length " + input.length + ",  compressed data length " + op.length);
        byte[] org = CompressionUtil.decompress(op);
        System.out.println(new String(op, StandardCharsets.UTF_8));
        System.out.println(new String(org, StandardCharsets.UTF_8));
    }

    @Test
    void compress() throws IOException {
        String op = CompressionUtil.compressB64(testStr);
        System.out.println("Compressed data b64" + op);
        String org = CompressionUtil.decompressB64(op);
        System.out.println("Original text" + org);
    }

    @Test
    void convertTime() {
        long second = 5;
        long minute = 4;
        long hour = 1;
        long millisecond = TimeUnit.SECONDS.toMillis(second);
        long millisecond2 = TimeUnit.MINUTES.toMillis(minute);
        long millisecond3 = TimeUnit.HOURS.toMillis(hour);
        log.info("second to millisecond {}", millisecond);
        log.info("minute to millisecond {}", millisecond2);
        log.info("hour to millisecond {}", millisecond3);
    }
}
