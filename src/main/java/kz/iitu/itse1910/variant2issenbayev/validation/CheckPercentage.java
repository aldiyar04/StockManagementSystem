package kz.iitu.itse1910.variant2issenbayev.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Min(value = 0, message = "Percentage cannot be < 0")
@Max(value = 100, message = "Percentage cannot be > 100")
@Constraint(validatedBy = {})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPercentage {
    String message() default "Percentage must be 0-100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}