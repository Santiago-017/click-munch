package com.bestellen.click_munch.user;

import java.util.List;

public record UserRequest(
        String name,
        String email,
        String username,
        String password,
        String phone
) {
}
