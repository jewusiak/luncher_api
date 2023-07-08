package pl.jewusiak.luncher_api.utils;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
public class Price {
    @Getter
    @Setter
    private BigDecimal amount;

    private Currency currency;

    public String getCurrency() {
        return currency.getCurrencyCode();
    }

    public void setCurrency(@Size(min = 3, max = 3) String currency) {
        this.currency=Currency.getInstance(currency);
    }
}
