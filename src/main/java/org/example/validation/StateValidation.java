package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.anno.State;

public class StateValidation implements ConstraintValidator<State, String> {//泛型<给哪个注解提供,数据类型>

    /**
     * @param value 将来要校验的数据
     * @return 如果返回false则校验不通过，如果返回true则通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (value == null) return false;
        return value.equals("已发布") || value.equals("草稿");
    }
}
