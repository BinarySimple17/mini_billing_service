package ru.binarysimple.billng.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.binarysimple.billng.model.Account;

public record AccountFilter(String username) {
    public Specification<Account> toSpecification() {
        return usernameSpec();
    }

    private Specification<Account> usernameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(username)
                ? cb.equal(root.get("username"), username)
                : null);
    }
}