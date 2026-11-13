package org.assignment.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.assignment.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbc;

    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

   
    private static final RowMapper<Employee> employeeMapper = (rs, rowNum) -> {
        Employee emp = new Employee();
        emp.setId(rs.getLong("id"));
        emp.setName(rs.getString("name"));
        emp.setEmail(rs.getString("email"));
        emp.setGrade(rs.getString("grade"));

        Date dojSql = rs.getDate("doj"); 
        emp.setDoj(dojSql);              

        String r = rs.getString("resigned"); 
        if (r != null && !r.isEmpty()) {
            emp.setResigned(r.charAt(0));
        }
        return emp;
    };

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM public.employee WHERE id = ?";
        List<Employee> list = jdbc.query(sql, employeeMapper, id);
        return list.stream().findFirst();
    }

    @Override
    public List<Employee> findByDOJ(Date doj) {
        String sql = "SELECT * FROM public.employee WHERE doj = ?";
        return jdbc.query(sql, employeeMapper, doj);
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM public.employee";
        return jdbc.query(sql, employeeMapper);
    }

    @Override
    public int insert(Employee e) {
        String sql = "INSERT INTO public.employee (name, email, grade, doj, resigned) VALUES (?, ?, ?, ?, ?)";

        Date dojSql = e.getDoj();                                
        String resignedStr = String.valueOf(e.getResigned())     
                                 .toUpperCase();

        return jdbc.update(sql, e.getName(), e.getEmail(), e.getGrade(), dojSql, resignedStr);
    }

    @Override
    public int update(Employee e) {
        String sql = "UPDATE public.employee SET name = ?, email = ?, grade = ?, doj = ?, resigned = ? WHERE id = ?";

        Date dojSql = e.getDoj();
        String resignedStr = String.valueOf(e.getResigned()).toUpperCase();

        return jdbc.update(sql, e.getName(), e.getEmail(), e.getGrade(), dojSql, resignedStr, e.getId());
    }

    @Override
    public int updateGrade(Long id, String grade) {
        String sql = "UPDATE public.employee SET grade = ? WHERE id = ?";
        return jdbc.update(sql, grade, id);
    }

    @Override
    public int deleteWhereResignedIsY() {
        String sql = "DELETE FROM public.employee WHERE resigned = 'Y'";
        return jdbc.update(sql);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM public.employee WHERE id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}