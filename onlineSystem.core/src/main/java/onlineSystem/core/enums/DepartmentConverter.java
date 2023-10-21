package onlineSystem.core.enums;

import org.springframework.core.convert.converter.Converter;

public class DepartmentConverter implements Converter<String, Department> {
    @Override
    public Department convert(String source) {
        return Department.valueOf(source); 
    }
}