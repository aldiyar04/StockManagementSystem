package kz.iitu.itse1910.variant2issenbayev.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DecimalMin(value = "0", inclusive = false, message = "Amount must be positive")
@Constraint(validatedBy = {})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAmountPositive {
    String message() default "Amount must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
