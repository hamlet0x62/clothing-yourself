package io.hamlet.projs.suit.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.hamlet.projs.suit.entity.User;

@JsonIgnoreProperties(value= {"password"})
public class UserInfoView extends User {
}
