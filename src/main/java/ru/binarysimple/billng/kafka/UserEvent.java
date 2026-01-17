package ru.binarysimple.billng.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.binarysimple.billng.model.NotificationType;


@Getter
@Setter
@NoArgsConstructor
public class UserEvent {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private NotificationType notificationType;
}
